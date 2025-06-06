package com.trade.marketdata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.marketdata.entity.StockBasic;
import com.trade.marketdata.entity.TushareResponse;
import com.trade.marketdata.mapper.StockBasicMapper;
import com.trade.marketdata.service.StockBasicService;
import com.trade.marketdata.service.TushareApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mac
 * @date 2024/7/16
 * @description 股票基本信息服务实现类
 */
@Service
@Slf4j
public class StockBasicServiceImpl extends ServiceImpl<StockBasicMapper, StockBasic> implements StockBasicService {

    private final TushareApi tushareApi;

    public StockBasicServiceImpl(TushareApi tushareApi) {
        this.tushareApi = tushareApi;
    }

    /**
     * 从 Tushare 同步股票基本信息
     * @return 同步的股票数量
     */
    @Override
    @Transactional
    public int syncStockBasicFromTushare() {
        log.info("开始从 Tushare 同步股票基本信息...");
        Map<String, String> params = new HashMap<>();
        params.put("exchange", ""); // 交易所代码，可选，空表示获取所有
        params.put("list_status", "L"); // 上市状态 L上市 D退市 P暂停上市

        String fields = "ts_code,symbol,name,area,province,city,industry,fullname,enname,market,exchange,curr_type,list_status,list_date,delist_date,is_hs";

        TushareResponse response = tushareApi.post("stock_basic", params, fields).block();

        if (response == null || response.getCode() != 0 || response.getData() == null) {
            log.error("从 Tushare 获取股票基本信息失败: {}", response != null ? response.getMsg() : "未知错误");
            return 0;
        }

        List<List<Object>> items = response.getData().getItems();
        List<String> fieldsList = response.getData().getFields();

        if (items == null || items.isEmpty()) {
            log.warn("从 Tushare 获取到空股票基本信息列表。");
            return 0;
        }

        List<StockBasic> newStockBasics = new ArrayList<>();
        for (List<Object> item : items) {
            StockBasic stockBasic = new StockBasic();
            for (int i = 0; i < fieldsList.size(); i++) {
                String fieldName = fieldsList.get(i);
                Object value = item.get(i);

                if (value == null) continue;

                switch (fieldName) {
                    case "ts_code": stockBasic.setTsCode(value.toString()); break;
                    case "symbol": stockBasic.setSymbol(value.toString()); break;
                    case "name": stockBasic.setName(value.toString()); break;
                    case "area": stockBasic.setArea(value.toString()); break;
                    case "province": stockBasic.setProvince(value.toString()); break;
                    case "city": stockBasic.setCity(value.toString()); break;
                    case "industry": stockBasic.setIndustry(value.toString()); break;
                    case "fullname": stockBasic.setFullname(value.toString()); break;
                    case "enname": stockBasic.setEnname(value.toString()); break;
                    case "market": stockBasic.setMarket(value.toString()); break;
                    case "exchange": stockBasic.setExchange(value.toString()); break;
                    case "curr_type": stockBasic.setCurrType(value.toString()); break;
                    case "list_status": stockBasic.setListStatus(value.toString()); break;
                    case "list_date": stockBasic.setListDate(LocalDate.parse(value.toString())); break;
                    case "delist_date": stockBasic.setDelistDate(value.toString().isEmpty() ? null : LocalDate.parse(value.toString())); break;
                    case "is_hs": stockBasic.setIsHs(value.toString()); break;
                }
            }
            stockBasic.setCreateTime(LocalDateTime.now());
            stockBasic.setUpdateTime(LocalDateTime.now());
            newStockBasics.add(stockBasic);
        }

        // 批量插入或更新
        // 考虑到数据量可能较大，且需要判断是否已存在，这里可以先查询现有数据，然后进行区分插入和更新
        // 简化处理：先删除所有现有数据，再批量插入新数据 (适用于数据量不大，且更新频率不高的场景)
        // 更优方案：根据 ts_code 判断是否存在，存在则更新，不存在则插入

        // 获取当前数据库中所有股票的 ts_code 集合
        List<String> existingTsCodes = baseMapper.selectList(null).stream()
                .map(StockBasic::getTsCode)
                .collect(Collectors.toList());

        List<StockBasic> toInsert = new ArrayList<>();
        List<StockBasic> toUpdate = new ArrayList<>();

        for (StockBasic stock : newStockBasics) {
            if (existingTsCodes.contains(stock.getTsCode())) {
                // 查找现有记录的ID，用于更新
                StockBasic existingStock = baseMapper.selectOne(com.baomidou.mybatisplus.core.conditions.query.QueryWrapper.<StockBasic>lambdaQuery().eq(StockBasic::getTsCode, stock.getTsCode()));
                if (existingStock != null) {
                    stock.setId(existingStock.getId());
                    toUpdate.add(stock);
                }
            } else {
                toInsert.add(stock);
            }
        }

        int insertedCount = 0;
        if (!toInsert.isEmpty()) {
            saveBatch(toInsert);
            insertedCount = toInsert.size();
            log.info("成功插入 {} 条新的股票基本信息。".formatted(insertedCount));
        }

        int updatedCount = 0;
        if (!toUpdate.isEmpty()) {
            updateBatchById(toUpdate);
            updatedCount = toUpdate.size();
            log.info("成功更新 {} 条股票基本信息。".formatted(updatedCount));
        }

        log.info("股票基本信息同步完成，总计插入 {} 条，更新 {} 条。".formatted(insertedCount, updatedCount));
        return insertedCount + updatedCount;
    }

    /**
     * 查询所有股票基本信息
     * @return 股票基本信息列表
     */
    @Override
    public List<StockBasic> listAllStockBasic() {
        return list();
    }
}
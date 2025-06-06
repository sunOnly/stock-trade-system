package com.trade.marketdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.marketdata.entity.DailyMarketData;
import com.trade.marketdata.entity.TushareResponse;
import com.trade.marketdata.mapper.DailyMarketDataMapper;
import com.trade.marketdata.service.DailyMarketDataService;
import com.trade.marketdata.service.TushareApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mac
 * @date 2024/7/16
 * @description 股票日线行情数据服务实现类
 */
@Service
@Slf4j
public class DailyMarketDataServiceImpl extends ServiceImpl<DailyMarketDataMapper, DailyMarketData> implements DailyMarketDataService {

    private final TushareApi tushareApi;

    public DailyMarketDataServiceImpl(TushareApi tushareApi) {
        this.tushareApi = tushareApi;
    }

    /**
     * 从 Tushare 同步指定股票的日线行情数据
     * @param tsCode 股票代码
     * @param startDate 开始日期 (yyyyMMdd)
     * @param endDate 结束日期 (yyyyMMdd)
     * @return 同步的日线数据数量
     */
    @Override
    @Transactional
    public int syncDailyMarketDataFromTushare(String tsCode, String startDate, String endDate) {
        log.info("开始从 Tushare 同步股票 {} 的日线行情数据，日期范围：{} 至 {}...", tsCode, startDate, endDate);
        Map<String, String> params = new HashMap<>();
        params.put("ts_code", tsCode);
        params.put("start_date", startDate);
        params.put("end_date", endDate);

        String fields = "ts_code,trade_date,open,high,low,close,pre_close,change,pct_chg,vol,amount";

        TushareResponse response = tushareApi.post("daily", params, fields).block();

        if (response == null || response.getCode() != 0 || response.getData() == null) {
            log.error("从 Tushare 获取股票 {} 日线行情数据失败: {}", tsCode, response != null ? response.getMsg() : "未知错误");
            return 0;
        }

        List<List<Object>> items = response.getData().getItems();
        List<String> fieldsList = response.getData().getFields();

        if (items == null || items.isEmpty()) {
            log.warn("从 Tushare 获取到股票 {} 的空日线行情数据列表。", tsCode);
            return 0;
        }

        List<DailyMarketData> newDailyDataList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        for (List<Object> item : items) {
            DailyMarketData dailyData = new DailyMarketData();
            for (int i = 0; i < fieldsList.size(); i++) {
                String fieldName = fieldsList.get(i);
                Object value = item.get(i);

                if (value == null) continue;

                switch (fieldName) {
                    case "ts_code": dailyData.setTsCode(value.toString()); break;
                    case "trade_date": dailyData.setTradeDate(LocalDate.parse(value.toString(), formatter)); break;
                    case "open": dailyData.setOpen(new BigDecimal(value.toString())); break;
                    case "high": dailyData.setHigh(new BigDecimal(value.toString())); break;
                    case "low": dailyData.setLow(new BigDecimal(value.toString())); break;
                    case "close": dailyData.setClose(new BigDecimal(value.toString())); break;
                    case "pre_close": dailyData.setPreClose(new BigDecimal(value.toString())); break;
                    case "change": dailyData.setChange(new BigDecimal(value.toString())); break;
                    case "pct_chg": dailyData.setPctChg(new BigDecimal(value.toString())); break;
                    case "vol": dailyData.setVol(new BigDecimal(value.toString())); break;
                    case "amount": dailyData.setAmount(new BigDecimal(value.toString())); break;
                }
            }
            dailyData.setCreateTime(LocalDateTime.now());
            dailyData.setUpdateTime(LocalDateTime.now());
            newDailyDataList.add(dailyData);
        }

        // 批量插入或更新
        // 获取当前数据库中指定股票在指定日期范围内的所有日线数据的 ts_code 和 trade_date 组合
        List<DailyMarketData> existingData = baseMapper.selectList(new QueryWrapper<DailyMarketData>()
                .eq("ts_code", tsCode)
                .between("trade_date", LocalDate.parse(startDate, formatter), LocalDate.parse(endDate, formatter)));

        Map<String, DailyMarketData> existingDataMap = existingData.stream()
                .collect(Collectors.toMap(data -> data.getTsCode() + "_" + data.getTradeDate().format(formatter), data -> data));

        List<DailyMarketData> toInsert = new ArrayList<>();
        List<DailyMarketData> toUpdate = new ArrayList<>();

        for (DailyMarketData newData : newDailyDataList) {
            String key = newData.getTsCode() + "_" + newData.getTradeDate().format(formatter);
            if (existingDataMap.containsKey(key)) {
                DailyMarketData existing = existingDataMap.get(key);
                newData.setId(existing.getId()); // 设置ID以便更新
                toUpdate.add(newData);
            } else {
                toInsert.add(newData);
            }
        }

        int insertedCount = 0;
        if (!toInsert.isEmpty()) {
            saveBatch(toInsert);
            insertedCount = toInsert.size();
            log.info("成功插入 {} 条新的股票 {} 日线行情数据。", insertedCount, tsCode);
        }

        int updatedCount = 0;
        if (!toUpdate.isEmpty()) {
            updateBatchById(toUpdate);
            updatedCount = toUpdate.size();
            log.info("成功更新 {} 条股票 {} 日线行情数据。", updatedCount, tsCode);
        }

        log.info("股票 {} 日线行情数据同步完成，总计插入 {} 条，更新 {} 条。", tsCode, insertedCount, updatedCount);
        return insertedCount + updatedCount;
    }

    /**
     * 查询指定股票在指定日期范围内的日线行情数据
     * @param tsCode 股票代码
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 日线行情数据列表
     */
    @Override
    public List<DailyMarketData> listDailyMarketData(String tsCode, LocalDate startDate, LocalDate endDate) {
        QueryWrapper<DailyMarketData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ts_code", tsCode);
        if (startDate != null) {
            queryWrapper.ge("trade_date", startDate);
        }
        if (endDate != null) {
            queryWrapper.le("trade_date", endDate);
        }
        queryWrapper.orderByAsc("trade_date");
        return list(queryWrapper);
    }
}
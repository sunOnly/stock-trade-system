package com.stock.marketdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stock.marketdata.dal.dataobject.MarketDataDO;
import com.stock.marketdata.dal.mapper.MarketDataMapper;
import com.stock.marketdata.service.MarketDataService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 行情数据服务实现类
 *
 * @author Pure AI
 */
@Service
public class MarketDataServiceImpl implements MarketDataService {

    @Resource
    private MarketDataMapper marketDataMapper;

    /**
     * 创建行情数据
     *
     * @param marketData 行情数据对象
     * @return 创建的行情数据ID
     */
    @Override
    public Long createMarketData(MarketDataDO marketData) {
        marketDataMapper.insert(marketData);
        return marketData.getId();
    }

    /**
     * 更新行情数据
     *
     * @param marketData 行情数据对象
     */
    @Override
    public void updateMarketData(MarketDataDO marketData) {
        marketDataMapper.updateById(marketData);
    }

    /**
     * 删除行情数据
     *
     * @param id 行情数据ID
     */
    @Override
    public void deleteMarketData(Long id) {
        marketDataMapper.deleteById(id);
    }

    /**
     * 获取行情数据
     *
     * @param id 行情数据ID
     * @return 行情数据对象
     */
    @Override
    public MarketDataDO getMarketData(Long id) {
        return marketDataMapper.selectById(id);
    }

    /**
     * 获取行情数据列表
     *
     * @param stockCode 股票代码
     * @return 行情数据列表
     */
    @Override
    public List<MarketDataDO> getMarketDataList(String stockCode) {
        LambdaQueryWrapper<MarketDataDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(stockCode)) {
            queryWrapper.eq(MarketDataDO::getStockCode, stockCode);
        }
        // TODO: 根据业务需求添加其他查询条件，例如时间范围等
        queryWrapper.orderByDesc(MarketDataDO::getTradingDay); // 默认按交易日降序
        return marketDataMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询行情数据
     *
     * @param page      分页参数
     * @param stockCode 股票代码
     * @return 分页结果
     */
    @Override
    public Page<MarketDataDO> pageMarketData(Page<MarketDataDO> page, String stockCode) {
        LambdaQueryWrapper<MarketDataDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(stockCode)) {
            queryWrapper.eq(MarketDataDO::getStockCode, stockCode);
        }
        // TODO: 根据业务需求添加其他查询条件，例如时间范围等
        queryWrapper.orderByDesc(MarketDataDO::getTradingDay); // 默认按交易日降序
        return marketDataMapper.selectPage(page, queryWrapper);
    }

    // TODO: 实现其他业务方法

}
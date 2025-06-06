package com.stock.marketdata.service;

import com.stock.marketdata.dal.dataobject.MarketDataDO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 行情数据服务接口
 *
 * @author Pure AI
 */
public interface MarketDataService {

    /**
     * 创建行情数据
     *
     * @param marketData 行情数据对象
     * @return 创建的行情数据ID
     */
    Long createMarketData(MarketDataDO marketData);

    /**
     * 更新行情数据
     *
     * @param marketData 行情数据对象
     */
    void updateMarketData(MarketDataDO marketData);

    /**
     * 删除行情数据
     *
     * @param id 行情数据ID
     */
    void deleteMarketData(Long id);

    /**
     * 获取行情数据
     *
     * @param id 行情数据ID
     * @return 行情数据对象
     */
    MarketDataDO getMarketData(Long id);

    /**
     * 获取行情数据列表
     *
     * @param stockCode 股票代码
     * @return 行情数据列表
     */
    List<MarketDataDO> getMarketDataList(String stockCode);

    /**
     * 分页查询行情数据
     *
     * @param page 分页参数
     * @param stockCode 股票代码
     * @return 分页结果
     */
    Page<MarketDataDO> pageMarketData(Page<MarketDataDO> page, String stockCode);

    // TODO: 添加其他业务方法

}
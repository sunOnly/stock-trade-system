package com.trade.marketdata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.marketdata.entity.StockBasic;

import java.util.List;

/**
 * @author mac
 * @date 2024/7/16
 * @description 股票基本信息服务接口
 */
public interface StockBasicService extends IService<StockBasic> {

    /**
     * 从 Tushare 同步股票基本信息
     * @return 同步的股票数量
     */
    int syncStockBasicFromTushare();

    /**
     * 查询所有股票基本信息
     * @return 股票基本信息列表
     */
    List<StockBasic> listAllStockBasic();
}
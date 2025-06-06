package com.trade.marketdata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.marketdata.entity.StockBasic;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mac
 * @date 2024/7/16
 * @description 股票基本信息 Mapper 接口
 */
@Mapper
public interface StockBasicMapper extends BaseMapper<StockBasic> {
}
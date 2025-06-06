package com.stock.trade.indicator.dal.mysql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stock.trade.framework.mybatis.core.mapper.BaseMapperX;
import com.stock.trade.indicator.dal.dataobject.IndicatorDefinitionDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 技术指标定义 Mapper
 *
 * @author tianxin
 */
@Mapper
public interface IndicatorDefinitionMapper extends BaseMapperX<IndicatorDefinitionDO> {

    /**
     * 根据指标名称查询指标定义
     *
     * @param name 指标名称
     * @return 指标定义
     */
    default IndicatorDefinitionDO selectByName(String name) {
        return selectOne(new LambdaQueryWrapper<IndicatorDefinitionDO>()
                .eq(IndicatorDefinitionDO::getName, name));
    }

    /**
     * 根据指标类型查询指标定义列表
     *
     * @param type 指标类型
     * @return 指标定义列表
     */
    default List<IndicatorDefinitionDO> selectListByType(String type) {
        return selectList(new LambdaQueryWrapper<IndicatorDefinitionDO>()
                .eq(IndicatorDefinitionDO::getType, type));
    }

    /**
     * 查询所有启用的指标定义
     *
     * @return 启用的指标定义列表
     */
    default List<IndicatorDefinitionDO> selectListByEnabled() {
        return selectList(new LambdaQueryWrapper<IndicatorDefinitionDO>()
                .eq(IndicatorDefinitionDO::getEnabled, true));
    }

}
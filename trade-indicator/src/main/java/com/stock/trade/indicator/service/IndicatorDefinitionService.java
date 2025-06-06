package com.stock.trade.indicator.service;

import com.stock.trade.framework.common.pojo.PageResult;
import com.stock.trade.indicator.controller.vo.IndicatorDefinitionCreateReqVO;
import com.stock.trade.indicator.controller.vo.IndicatorDefinitionPageReqVO;
import com.stock.trade.indicator.controller.vo.IndicatorDefinitionUpdateReqVO;
import com.stock.trade.indicator.dal.dataobject.IndicatorDefinitionDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 技术指标定义 Service 接口
 *
 * @author tianxin
 */
public interface IndicatorDefinitionService {

    /**
     * 创建技术指标定义
     *
     * @param createReqVO 创建信息
     * @return 指标定义ID
     */
    Long createIndicatorDefinition(@Valid IndicatorDefinitionCreateReqVO createReqVO);

    /**
     * 更新技术指标定义
     *
     * @param updateReqVO 更新信息
     */
    void updateIndicatorDefinition(@Valid IndicatorDefinitionUpdateReqVO updateReqVO);

    /**
     * 删除技术指标定义
     *
     * @param id 指标定义ID
     */
    void deleteIndicatorDefinition(Long id);

    /**
     * 获取技术指标定义
     *
     * @param id 指标定义ID
     * @return 指标定义
     */
    IndicatorDefinitionDO getIndicatorDefinition(Long id);

    /**
     * 获取技术指标定义列表
     *
     * @param ids 指标定义ID列表
     * @return 指标定义列表
     */
    List<IndicatorDefinitionDO> getIndicatorDefinitionList(List<Long> ids);

    /**
     * 获取技术指标定义分页
     *
     * @param pageReqVO 分页查询
     * @return 指标定义分页
     */
    PageResult<IndicatorDefinitionDO> getIndicatorDefinitionPage(IndicatorDefinitionPageReqVO pageReqVO);

    /**
     * 根据指标名称获取技术指标定义
     *
     * @param name 指标名称
     * @return 指标定义
     */
    IndicatorDefinitionDO getIndicatorDefinitionByName(String name);

    /**
     * 根据指标类型获取技术指标定义列表
     *
     * @param type 指标类型
     * @return 指标定义列表
     */
    List<IndicatorDefinitionDO> getIndicatorDefinitionListByType(String type);

    /**
     * 获取所有启用的技术指标定义
     *
     * @return 启用的指标定义列表
     */
    List<IndicatorDefinitionDO> getEnabledIndicatorDefinitionList();

    /**
     * 更新技术指标定义状态
     *
     * @param id 指标定义ID
     * @param enabled 是否启用
     */
    void updateIndicatorDefinitionStatus(Long id, Boolean enabled);

}
package com.stock.trade.indicator.service.impl;

import com.stock.trade.framework.common.pojo.PageResult;
import com.stock.trade.indicator.controller.vo.IndicatorDefinitionCreateReqVO;
import com.stock.trade.indicator.controller.vo.IndicatorDefinitionPageReqVO;
import com.stock.trade.indicator.controller.vo.IndicatorDefinitionUpdateReqVO;
import com.stock.trade.indicator.convert.IndicatorDefinitionConvert;
import com.stock.trade.indicator.dal.dataobject.IndicatorDefinitionDO;
import com.stock.trade.indicator.dal.mysql.IndicatorDefinitionMapper;
import com.stock.trade.indicator.service.IndicatorDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static com.stock.trade.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.stock.trade.indicator.enums.ErrorCodeConstants.*;

/**
 * 技术指标定义 Service 实现类
 *
 * @author tianxin
 */
@Service
@Validated
@Slf4j
public class IndicatorDefinitionServiceImpl implements IndicatorDefinitionService {

    @Resource
    private IndicatorDefinitionMapper indicatorDefinitionMapper;

    /**
     * 创建技术指标定义
     *
     * @param createReqVO 创建信息
     * @return 指标定义ID
     */
    @Override
    public Long createIndicatorDefinition(IndicatorDefinitionCreateReqVO createReqVO) {
        // 校验名称唯一性
        validateIndicatorDefinitionNameUnique(null, createReqVO.getName());
        // 插入
        IndicatorDefinitionDO indicatorDefinition = IndicatorDefinitionConvert.INSTANCE.convert(createReqVO);
        indicatorDefinitionMapper.insert(indicatorDefinition);
        // 返回
        return indicatorDefinition.getId();
    }

    /**
     * 更新技术指标定义
     *
     * @param updateReqVO 更新信息
     */
    @Override
    public void updateIndicatorDefinition(IndicatorDefinitionUpdateReqVO updateReqVO) {
        // 校验存在
        validateIndicatorDefinitionExists(updateReqVO.getId());
        // 校验名称唯一性
        validateIndicatorDefinitionNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        IndicatorDefinitionDO updateObj = IndicatorDefinitionConvert.INSTANCE.convert(updateReqVO);
        indicatorDefinitionMapper.updateById(updateObj);
    }

    /**
     * 删除技术指标定义
     *
     * @param id 指标定义ID
     */
    @Override
    public void deleteIndicatorDefinition(Long id) {
        // 校验存在
        validateIndicatorDefinitionExists(id);
        // 删除
        indicatorDefinitionMapper.deleteById(id);
    }

    /**
     * 校验技术指标定义是否存在
     *
     * @param id 指标定义ID
     */
    private void validateIndicatorDefinitionExists(Long id) {
        if (indicatorDefinitionMapper.selectById(id) == null) {
            throw exception(INDICATOR_DEFINITION_NOT_EXISTS);
        }
    }

    /**
     * 校验技术指标定义名称的唯一性
     *
     * @param id   指标定义ID，可为空，用于更新时排除自身
     * @param name 指标名称
     */
    private void validateIndicatorDefinitionNameUnique(Long id, String name) {
        IndicatorDefinitionDO definition = indicatorDefinitionMapper.selectByName(name);
        if (definition == null) {
            return;
        }
        // 如果 id 为空，说明不用排除自身
        if (id == null) {
            throw exception(INDICATOR_DEFINITION_NAME_DUPLICATE);
        }
        if (!definition.getId().equals(id)) {
            throw exception(INDICATOR_DEFINITION_NAME_DUPLICATE);
        }
    }

    /**
     * 获取技术指标定义
     *
     * @param id 指标定义ID
     * @return 指标定义
     */
    @Override
    public IndicatorDefinitionDO getIndicatorDefinition(Long id) {
        return indicatorDefinitionMapper.selectById(id);
    }

    /**
     * 获取技术指标定义列表
     *
     * @param ids 指标定义ID列表
     * @return 指标定义列表
     */
    @Override
    public List<IndicatorDefinitionDO> getIndicatorDefinitionList(List<Long> ids) {
        return indicatorDefinitionMapper.selectBatchIds(ids);
    }

    /**
     * 获取技术指标定义分页
     *
     * @param pageReqVO 分页查询
     * @return 指标定义分页
     */
    @Override
    public PageResult<IndicatorDefinitionDO> getIndicatorDefinitionPage(IndicatorDefinitionPageReqVO pageReqVO) {
        return indicatorDefinitionMapper.selectPage(pageReqVO, IndicatorDefinitionConvert.INSTANCE.convert(pageReqVO));
    }

    /**
     * 根据指标名称获取技术指标定义
     *
     * @param name 指标名称
     * @return 指标定义
     */
    @Override
    public IndicatorDefinitionDO getIndicatorDefinitionByName(String name) {
        return indicatorDefinitionMapper.selectByName(name);
    }

    /**
     * 根据指标类型获取技术指标定义列表
     *
     * @param type 指标类型
     * @return 指标定义列表
     */
    @Override
    public List<IndicatorDefinitionDO> getIndicatorDefinitionListByType(String type) {
        return indicatorDefinitionMapper.selectListByType(type);
    }

    /**
     * 获取所有启用的技术指标定义
     *
     * @return 启用的指标定义列表
     */
    @Override
    public List<IndicatorDefinitionDO> getEnabledIndicatorDefinitionList() {
        return indicatorDefinitionMapper.selectListByEnabled(true);
    }

    /**
     * 更新技术指标定义状态
     *
     * @param id      指标定义ID
     * @param enabled 是否启用
     */
    @Override
    public void updateIndicatorDefinitionStatus(Long id, Boolean enabled) {
        // 校验存在
        validateIndicatorDefinitionExists(id);
        // 更新状态
        IndicatorDefinitionDO updateObj = new IndicatorDefinitionDO();
        updateObj.setId(id);
        updateObj.setEnabled(enabled);
        indicatorDefinitionMapper.updateById(updateObj);
    }
}
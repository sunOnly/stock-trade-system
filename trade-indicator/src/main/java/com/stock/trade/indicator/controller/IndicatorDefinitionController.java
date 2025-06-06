package com.stock.trade.indicator.controller;

import com.stock.trade.framework.common.pojo.CommonResult;
import com.stock.trade.framework.common.pojo.PageResult;
import com.stock.trade.indicator.controller.vo.IndicatorDefinitionCreateReqVO;
import com.stock.trade.indicator.controller.vo.IndicatorDefinitionPageReqVO;
import com.stock.trade.indicator.controller.vo.IndicatorDefinitionRespVO;
import com.stock.trade.indicator.controller.vo.IndicatorDefinitionUpdateReqVO;
import com.stock.trade.indicator.convert.IndicatorDefinitionConvert;
import com.stock.trade.indicator.dal.dataobject.IndicatorDefinitionDO;
import com.stock.trade.indicator.service.IndicatorDefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.stock.trade.framework.common.pojo.CommonResult.success;

/**
 * 技术指标定义 Controller
 *
 * @author tianxin
 */
@Tag(name = "管理后台 - 技术指标定义")
@RestController
@RequestMapping("/indicator/definition")
@Validated
public class IndicatorDefinitionController {

    @Resource
    private IndicatorDefinitionService indicatorDefinitionService;

    /**
     * 创建技术指标定义
     *
     * @param createReqVO 创建信息
     * @return 指标定义ID
     */
    @PostMapping("/create")
    @Operation(summary = "创建技术指标定义")
    @PreAuthorize("@ss.hasPermission('indicator:definition:create')")
    public CommonResult<Long> createIndicatorDefinition(@Valid @RequestBody IndicatorDefinitionCreateReqVO createReqVO) {
        return success(indicatorDefinitionService.createIndicatorDefinition(createReqVO));
    }

    /**
     * 更新技术指标定义
     *
     * @param updateReqVO 更新信息
     * @return 是否成功
     */
    @PutMapping("/update")
    @Operation(summary = "更新技术指标定义")
    @PreAuthorize("@ss.hasPermission('indicator:definition:update')")
    public CommonResult<Boolean> updateIndicatorDefinition(@Valid @RequestBody IndicatorDefinitionUpdateReqVO updateReqVO) {
        indicatorDefinitionService.updateIndicatorDefinition(updateReqVO);
        return success(true);
    }

    /**
     * 更新技术指标定义状态
     *
     * @param id 指标定义ID
     * @param enabled 是否启用
     * @return 是否成功
     */
    @PutMapping("/update-status")
    @Operation(summary = "更新技术指标定义状态")
    @PreAuthorize("@ss.hasPermission('indicator:definition:update')")
    public CommonResult<Boolean> updateIndicatorDefinitionStatus(@RequestParam("id") Long id,
                                                               @RequestParam("enabled") Boolean enabled) {
        indicatorDefinitionService.updateIndicatorDefinitionStatus(id, enabled);
        return success(true);
    }

    /**
     * 删除技术指标定义
     *
     * @param id 指标定义ID
     * @return 是否成功
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除技术指标定义")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('indicator:definition:delete')")
    public CommonResult<Boolean> deleteIndicatorDefinition(@RequestParam("id") Long id) {
        indicatorDefinitionService.deleteIndicatorDefinition(id);
        return success(true);
    }

    /**
     * 获取技术指标定义
     *
     * @param id 指标定义ID
     * @return 指标定义信息
     */
    @GetMapping("/get")
    @Operation(summary = "获得技术指标定义")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('indicator:definition:query')")
    public CommonResult<IndicatorDefinitionRespVO> getIndicatorDefinition(@RequestParam("id") Long id) {
        IndicatorDefinitionDO definition = indicatorDefinitionService.getIndicatorDefinition(id);
        return success(IndicatorDefinitionConvert.INSTANCE.convert(definition));
    }

    /**
     * 获取技术指标定义列表
     *
     * @param ids 指标定义ID列表
     * @return 指标定义列表
     */
    @GetMapping("/list")
    @Operation(summary = "获得技术指标定义列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('indicator:definition:query')")
    public CommonResult<List<IndicatorDefinitionRespVO>> getIndicatorDefinitionList(@RequestParam("ids") List<Long> ids) {
        List<IndicatorDefinitionDO> list = indicatorDefinitionService.getIndicatorDefinitionList(ids);
        return success(IndicatorDefinitionConvert.INSTANCE.convertList(list));
    }

    /**
     * 获取技术指标定义分页
     *
     * @param pageVO 分页查询参数
     * @return 指标定义分页结果
     */
    @GetMapping("/page")
    @Operation(summary = "获得技术指标定义分页")
    @PreAuthorize("@ss.hasPermission('indicator:definition:query')")
    public CommonResult<PageResult<IndicatorDefinitionRespVO>> getIndicatorDefinitionPage(@Valid IndicatorDefinitionPageReqVO pageVO) {
        PageResult<IndicatorDefinitionDO> pageResult = indicatorDefinitionService.getIndicatorDefinitionPage(pageVO);
        return success(IndicatorDefinitionConvert.INSTANCE.convertPage(pageResult));
    }

    /**
     * 根据名称获取技术指标定义
     *
     * @param name 指标名称
     * @return 指标定义信息
     */
    @GetMapping("/get-by-name")
    @Operation(summary = "根据名称获得技术指标定义")
    @Parameter(name = "name", description = "指标名称", required = true, example = "SMA")
    @PreAuthorize("@ss.hasPermission('indicator:definition:query')")
    public CommonResult<IndicatorDefinitionRespVO> getIndicatorDefinitionByName(@RequestParam("name") String name) {
        IndicatorDefinitionDO definition = indicatorDefinitionService.getIndicatorDefinitionByName(name);
        return success(IndicatorDefinitionConvert.INSTANCE.convert(definition));
    }

    /**
     * 根据类型获取技术指标定义列表
     *
     * @param type 指标类型
     * @return 指标定义列表
     */
    @GetMapping("/list-by-type")
    @Operation(summary = "根据类型获得技术指标定义列表")
    @Parameter(name = "type", description = "指标类型", required = true, example = "TREND")
    @PreAuthorize("@ss.hasPermission('indicator:definition:query')")
    public CommonResult<List<IndicatorDefinitionRespVO>> getIndicatorDefinitionListByType(@RequestParam("type") String type) {
        List<IndicatorDefinitionDO> list = indicatorDefinitionService.getIndicatorDefinitionListByType(type);
        return success(IndicatorDefinitionConvert.INSTANCE.convertList(list));
    }

    /**
     * 获取所有启用的技术指标定义列表
     *
     * @return 启用的指标定义列表
     */
    @GetMapping("/list-enabled")
    @Operation(summary = "获得所有启用的技术指标定义列表")
    @PreAuthorize("@ss.hasPermission('indicator:definition:query')")
    public CommonResult<List<IndicatorDefinitionRespVO>> getEnabledIndicatorDefinitionList() {
        List<IndicatorDefinitionDO> list = indicatorDefinitionService.getEnabledIndicatorDefinitionList();
        return success(IndicatorDefinitionConvert.INSTANCE.convertList(list));
    }
}
package com.stock.trade.indicator.controller;

import com.stock.trade.framework.common.pojo.CommonResult;
import com.stock.trade.framework.common.pojo.PageResult;
import com.stock.trade.indicator.controller.vo.indicator.value.*;
import com.stock.trade.indicator.convert.IndicatorValueConvert;
import com.stock.trade.indicator.dal.dataobject.IndicatorValueDO;
import com.stock.trade.indicator.service.IndicatorValueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static com.stock.trade.framework.common.pojo.CommonResult.success;
import static com.stock.trade.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

/**
 * 技术指标计算结果 Controller
 *
 * @author AI Assistant
 */
@Tag(name = "管理后台 - 技术指标计算结果")
@RestController
@RequestMapping("/indicator/value")
@Validated
public class IndicatorValueController {

    @Resource
    private IndicatorValueService indicatorValueService;

    /**
     * 创建指标计算结果
     *
     * @param createReqVO 创建信息
     * @return 指标计算结果ID
     */
    @PostMapping("/create")
    @Operation(summary = "创建指标计算结果")
    @PreAuthorize("@ss.hasPermission('indicator:value:create')")
    public CommonResult<Long> createIndicatorValue(@Valid @RequestBody IndicatorValueCreateReqVO createReqVO) {
        return success(indicatorValueService.createIndicatorValue(createReqVO));
    }

    /**
     * 更新指标计算结果
     *
     * @param updateReqVO 更新信息
     * @return 是否成功
     */
    @PutMapping("/update")
    @Operation(summary = "更新指标计算结果")
    @PreAuthorize("@ss.hasPermission('indicator:value:update')")
    public CommonResult<Boolean> updateIndicatorValue(@Valid @RequestBody IndicatorValueUpdateReqVO updateReqVO) {
        indicatorValueService.updateIndicatorValue(updateReqVO);
        return success(true);
    }

    /**
     * 删除指标计算结果
     *
     * @param id 指标计算结果ID
     * @return 是否成功
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除指标计算结果")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('indicator:value:delete')")
    public CommonResult<Boolean> deleteIndicatorValue(@RequestParam("id") Long id) {
        indicatorValueService.deleteIndicatorValue(id);
        return success(true);
    }

    /**
     * 获取指标计算结果
     *
     * @param id 指标计算结果ID
     * @return 指标计算结果信息
     */
    @GetMapping("/get")
    @Operation(summary = "获得指标计算结果")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('indicator:value:query')")
    public CommonResult<IndicatorValueRespVO> getIndicatorValue(@RequestParam("id") Long id) {
        IndicatorValueDO value = indicatorValueService.getIndicatorValue(id);
        return success(IndicatorValueConvert.INSTANCE.convert(value));
    }

    /**
     * 获取指标计算结果列表
     *
     * @param ids 指标计算结果ID列表
     * @return 指标计算结果列表
     */
    @GetMapping("/list")
    @Operation(summary = "获得指标计算结果列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('indicator:value:query')")
    public CommonResult<List<IndicatorValueRespVO>> getIndicatorValueList(@RequestParam("ids") List<Long> ids) {
        List<IndicatorValueDO> list = indicatorValueService.getIndicatorValueList(ids);
        return success(IndicatorValueConvert.INSTANCE.convertList(list));
    }

    /**
     * 获取指标计算结果分页
     *
     * @param pageVO 分页查询参数
     * @return 指标计算结果分页结果
     */
    @GetMapping("/page")
    @Operation(summary = "获得指标计算结果分页")
    @PreAuthorize("@ss.hasPermission('indicator:value:query')")
    public CommonResult<PageResult<IndicatorValueRespVO>> getIndicatorValuePage(@Valid IndicatorValuePageReqVO pageVO) {
        PageResult<IndicatorValueDO> pageResult = indicatorValueService.getIndicatorValuePage(pageVO);
        return success(IndicatorValueConvert.INSTANCE.convertPage(pageResult));
    }

    /**
     * 导出指标计算结果 Excel
     *
     * @param response HTTP响应
     * @param exportReqVO 查询条件
     * @throws IOException IO异常
     */
    @GetMapping("/export-excel")
    @Operation(summary = "导出指标计算结果 Excel")
    @PreAuthorize("@ss.hasPermission('indicator:value:export')")
    // @OperateLog(type = EXPORT) // 假设有操作日志注解
    public void exportIndicatorValueExcel(HttpServletResponse response, @Valid IndicatorValueExportReqVO exportReqVO) throws IOException {
        List<IndicatorValueDO> list = indicatorValueService.getIndicatorValueList(exportReqVO);
        List<IndicatorValueExcelVO> excelList = IndicatorValueConvert.INSTANCE.convertList02(list);
        // ExcelUtils.write(response, "指标计算结果.xls", "数据", IndicatorValueExcelVO.class, excelList); // 假设有Excel导出工具类
    }

    /**
     * 获取最新的指标计算结果
     *
     * @param definitionId 指标定义ID
     * @param stockCode 股票代码
     * @param period 时间周期
     * @return 最新的指标计算结果
     */
    @GetMapping("/get-latest")
    @Operation(summary = "获取最新的指标计算结果")
    @Parameters({
            @Parameter(name = "definitionId", description = "指标定义ID", required = true, example = "1"),
            @Parameter(name = "stockCode", description = "股票代码", required = true, example = "600000.SH"),
            @Parameter(name = "period", description = "时间周期", required = true, example = "DAY")
    })
    @PreAuthorize("@ss.hasPermission('indicator:value:query')")
    public CommonResult<IndicatorValueRespVO> getLatestIndicatorValue(@RequestParam("definitionId") Long definitionId,
                                                                      @RequestParam("stockCode") String stockCode,
                                                                      @RequestParam("period") String period) {
        IndicatorValueDO value = indicatorValueService.getLatestIndicatorValue(definitionId, stockCode, period);
        return success(IndicatorValueConvert.INSTANCE.convert(value));
    }

    /**
     * 获取指定时间范围内的指标计算结果列表
     *
     * @param definitionId 指标定义ID
     * @param stockCode 股票代码
     * @param period 时间周期
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 指标计算结果列表
     */
    @GetMapping("/list-by-time-range")
    @Operation(summary = "获取指定时间范围内的指标计算结果列表")
    @Parameters({
            @Parameter(name = "definitionId", description = "指标定义ID", required = true, example = "1"),
            @Parameter(name = "stockCode", description = "股票代码", required = true, example = "600000.SH"),
            @Parameter(name = "period", description = "时间周期", required = true, example = "DAY"),
            @Parameter(name = "startTime", description = "开始时间", required = true, example = "2023-01-01T00:00:00"),
            @Parameter(name = "endTime", description = "结束时间", required = true, example = "2023-12-31T23:59:59")
    })
    @PreAuthorize("@ss.hasPermission('indicator:value:query')")
    public CommonResult<List<IndicatorValueRespVO>> getIndicatorValueListByTimeRange(
            @RequestParam("definitionId") Long definitionId,
            @RequestParam("stockCode") String stockCode,
            @RequestParam("period") String period,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<IndicatorValueDO> list = indicatorValueService.getIndicatorValueListByTimeRange(definitionId, stockCode, period, startTime, endTime);
        return success(IndicatorValueConvert.INSTANCE.convertList(list));
    }

    /**
     * 获取指定状态的指标计算结果列表
     *
     * @param status 计算状态
     * @return 指标计算结果列表
     */
    @GetMapping("/list-by-status")
    @Operation(summary = "获取指定状态的指标计算结果列表")
    @Parameter(name = "status", description = "计算状态", required = true, example = "SUCCESS")
    @PreAuthorize("@ss.hasPermission('indicator:value:query')")
    public CommonResult<List<IndicatorValueRespVO>> getIndicatorValueListByStatus(@RequestParam("status") String status) {
        List<IndicatorValueDO> list = indicatorValueService.getIndicatorValueListByStatus(status);
        return success(IndicatorValueConvert.INSTANCE.convertList(list));
    }

    /**
     * 批量更新指标计算结果状态
     *
     * @param updateStatusReqVO 更新状态请求
     * @return 是否成功
     */
    @PutMapping("/update-status-batch")
    @Operation(summary = "批量更新指标计算结果状态")
    @PreAuthorize("@ss.hasPermission('indicator:value:update')")
    public CommonResult<Boolean> updateIndicatorValueStatusBatch(@Valid @RequestBody IndicatorValueUpdateStatusBatchReqVO updateStatusReqVO) {
        indicatorValueService.updateIndicatorValueStatusBatch(updateStatusReqVO.getIds(), updateStatusReqVO.getStatus(), updateStatusReqVO.getErrorMessage());
        return success(true);
    }

    /**
     * 触发指标计算
     *
     * @param calculateReqVO 计算请求信息
     * @return 指标计算结果ID列表
     */
    @PostMapping("/calculate")
    @Operation(summary = "触发指标计算")
    @PreAuthorize("@ss.hasPermission('indicator:value:calculate')")
    public CommonResult<List<Long>> calculateIndicatorValue(@Valid @RequestBody IndicatorValueCalculateReqVO calculateReqVO) {
        List<Long> resultIds = indicatorValueService.calculateIndicatorValue(calculateReqVO);
        return success(resultIds);
    }
}
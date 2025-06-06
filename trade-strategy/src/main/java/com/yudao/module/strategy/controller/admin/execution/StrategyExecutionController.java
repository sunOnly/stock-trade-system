package com.yudao.module.strategy.controller.admin.execution;

import com.yudao.framework.common.pojo.CommonResult;
import com.yudao.framework.common.pojo.PageResult;
import com.yudao.module.strategy.controller.admin.execution.vo.*;
import com.yudao.module.strategy.convert.execution.StrategyExecutionConvert;
import com.yudao.module.strategy.dal.dataobject.execution.StrategyExecution;
import com.yudao.module.strategy.service.execution.StrategyExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static com.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 策略执行")
@RestController
@RequestMapping("/strategy/execution")
@Validated
public class StrategyExecutionController {

    @Resource
    private StrategyExecutionService strategyExecutionService;

    @PostMapping("/create")
    @Operation(summary = "创建策略执行")
    @PreAuthorize("@ss.hasPermission('strategy:execution:create')")
    public CommonResult<Long> createStrategyExecution(@Valid @RequestBody StrategyExecutionCreateReqVO createReqVO) {
        return success(strategyExecutionService.createStrategyExecution(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新策略执行")
    @PreAuthorize("@ss.hasPermission('strategy:execution:update')")
    public CommonResult<Boolean> updateStrategyExecution(@Valid @RequestBody StrategyExecutionUpdateReqVO updateReqVO) {
        strategyExecutionService.updateStrategyExecution(updateReqVO);
        return success(true);
    }

    @PutMapping("/start")
    @Operation(summary = "启动策略执行")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strategy:execution:start')")
    public CommonResult<Boolean> startStrategyExecution(@RequestParam("id") Long id) {
        strategyExecutionService.startStrategyExecution(id);
        return success(true);
    }

    @PutMapping("/pause")
    @Operation(summary = "暂停策略执行")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strategy:execution:pause')")
    public CommonResult<Boolean> pauseStrategyExecution(@RequestParam("id") Long id) {
        strategyExecutionService.pauseStrategyExecution(id);
        return success(true);
    }

    @PutMapping("/stop")
    @Operation(summary = "停止策略执行")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strategy:execution:stop')")
    public CommonResult<Boolean> stopStrategyExecution(@RequestParam("id") Long id) {
        strategyExecutionService.stopStrategyExecution(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除策略执行")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strategy:execution:delete')")
    public CommonResult<Boolean> deleteStrategyExecution(@RequestParam("id") Long id) {
        strategyExecutionService.deleteStrategyExecution(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得策略执行")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strategy:execution:query')")
    public CommonResult<StrategyExecutionRespVO> getStrategyExecution(@RequestParam("id") Long id) {
        StrategyExecution execution = strategyExecutionService.getStrategyExecution(id);
        return success(StrategyExecutionConvert.INSTANCE.convert(execution));
    }

    @GetMapping("/list")
    @Operation(summary = "获得策略执行列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('strategy:execution:query')")
    public CommonResult<List<StrategyExecutionRespVO>> getStrategyExecutionList(@RequestParam("ids") Collection<Long> ids) {
        List<StrategyExecution> list = strategyExecutionService.getStrategyExecutionList(ids);
        return success(StrategyExecutionConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得策略执行分页")
    @PreAuthorize("@ss.hasPermission('strategy:execution:query')")
    public CommonResult<PageResult<StrategyExecutionRespVO>> getStrategyExecutionPage(@Valid StrategyExecutionPageReqVO pageVO) {
        PageResult<StrategyExecution> pageResult = strategyExecutionService.getStrategyExecutionPage(pageVO);
        return success(StrategyExecutionConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/list-by-definition")
    @Operation(summary = "根据策略定义ID获得策略执行列表")
    @Parameter(name = "strategyDefinitionId", description = "策略定义ID", required = true, example = "100")
    @PreAuthorize("@ss.hasPermission('strategy:execution:query')")
    public CommonResult<List<StrategyExecutionRespVO>> getStrategyExecutionsByDefinitionId(@RequestParam("strategyDefinitionId") Long strategyDefinitionId) {
        List<StrategyExecution> list = strategyExecutionService.getStrategyExecutionsByDefinitionId(strategyDefinitionId);
        return success(StrategyExecutionConvert.INSTANCE.convertList(list));
    }

}
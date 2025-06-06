package com.stock.trade.strategy.controller;

import com.stock.trade.framework.common.pojo.CommonResult;
import com.stock.trade.framework.common.pojo.PageResult;
import com.stock.trade.strategy.controller.vo.StrategyExecutionCreateReqVO;
import com.stock.trade.strategy.controller.vo.StrategyExecutionPageReqVO;
import com.stock.trade.strategy.controller.vo.StrategyExecutionRespVO;
import com.stock.trade.strategy.controller.vo.StrategyExecutionUpdateReqVO;
import com.stock.trade.strategy.convert.StrategyExecutionConvert;
import com.stock.trade.strategy.dal.dataobject.StrategyExecutionDO;
import com.stock.trade.strategy.service.StrategyExecutionService;
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

@Tag(name = "管理后台 - 策略执行")
@RestController
@RequestMapping("/trade/strategy-execution")
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

    @PutMapping("/start/{id}")
    @Operation(summary = "启动策略执行")
    @Parameter(name = "id", description = "策略执行编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strategy:execution:start')")
    public CommonResult<Boolean> startStrategyExecution(@PathVariable("id") Long id) {
        strategyExecutionService.startStrategyExecution(id);
        return success(true);
    }

    @PutMapping("/pause/{id}")
    @Operation(summary = "暂停策略执行")
    @Parameter(name = "id", description = "策略执行编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strategy:execution:pause')")
    public CommonResult<Boolean> pauseStrategyExecution(@PathVariable("id") Long id) {
        strategyExecutionService.pauseStrategyExecution(id);
        return success(true);
    }

    @PutMapping("/stop/{id}")
    @Operation(summary = "停止策略执行")
    @Parameter(name = "id", description = "策略执行编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strategy:execution:stop')")
    public CommonResult<Boolean> stopStrategyExecution(@PathVariable("id") Long id) {
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
        StrategyExecutionDO execution = strategyExecutionService.getStrategyExecution(id);
        return success(StrategyExecutionConvert.INSTANCE.convert(execution));
    }

    @GetMapping("/page")
    @Operation(summary = "获得策略执行分页")
    @PreAuthorize("@ss.hasPermission('strategy:execution:query')")
    public CommonResult<PageResult<StrategyExecutionRespVO>> getStrategyExecutionPage(@Valid StrategyExecutionPageReqVO pageVO) {
        PageResult<StrategyExecutionDO> pageResult = strategyExecutionService.getStrategyExecutionPage(pageVO);
        return success(StrategyExecutionConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/list-by-definition")
    @Operation(summary = "根据策略定义ID获得策略执行列表")
    @Parameter(name = "strategyDefinitionId", description = "策略定义编号", required = true, example = "2048")
    @PreAuthorize("@ss.hasPermission('strategy:execution:query')")
    public CommonResult<List<StrategyExecutionRespVO>> getStrategyExecutionListByDefinitionId(@RequestParam("strategyDefinitionId") Long strategyDefinitionId) {
        List<StrategyExecutionDO> list = strategyExecutionService.getStrategyExecutionListByDefinitionId(strategyDefinitionId);
        return success(StrategyExecutionConvert.INSTANCE.convertList(list));
    }

}
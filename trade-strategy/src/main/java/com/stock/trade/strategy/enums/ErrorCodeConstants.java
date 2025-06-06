package com.stock.trade.strategy.enums;

import com.stock.trade.framework.common.exception.ErrorCode;

/**
 * Trade Strategy 模块错误码常量
 *
 * @author AI Assistant
 */
public interface ErrorCodeConstants {

    // ========== 策略定义 模块 1-001-000-000 ==========
    ErrorCode STRATEGY_DEFINITION_NOT_EXISTS = new ErrorCode(1001000000, "策略定义不存在");
    ErrorCode STRATEGY_DEFINITION_NAME_EXISTS = new ErrorCode(1001000001, "策略定义名称已存在");
    ErrorCode STRATEGY_DEFINITION_CANNOT_DELETE_WHEN_ACTIVE = new ErrorCode(1001000002, "策略定义处于活动状态，无法删除");


    // ========== 策略回测 模块 1-001-001-000 ==========
    ErrorCode STRATEGY_BACKTEST_NOT_EXISTS = new ErrorCode(1001001000, "策略回测记录不存在");
    ErrorCode STRATEGY_BACKTEST_CANNOT_START = new ErrorCode(1001001001, "当前状态无法启动策略回测");
    ErrorCode STRATEGY_BACKTEST_CANNOT_UPDATE_NON_PENDING = new ErrorCode(1001001002, "只有待处理状态的回测才能更新");
    ErrorCode STRATEGY_BACKTEST_CANNOT_DELETE_WHEN_RUNNING = new ErrorCode(1001001003, "运行中的策略回测无法删除");


    // ========== 策略执行 模块 1-001-002-000 ==========
    ErrorCode STRATEGY_EXECUTION_NOT_EXISTS = new ErrorCode(1001002000, "策略执行记录不存在");
    ErrorCode STRATEGY_EXECUTION_STATUS_CANNOT_START = new ErrorCode(1001002001, "当前状态无法启动策略执行");
    ErrorCode STRATEGY_EXECUTION_STATUS_CANNOT_PAUSE = new ErrorCode(1001002002, "当前状态无法暂停策略执行");
    ErrorCode STRATEGY_EXECUTION_STATUS_CANNOT_STOP = new ErrorCode(1001002003, "当前状态无法停止策略执行");
    ErrorCode STRATEGY_EXECUTION_STATUS_CANNOT_COMPLETE = new ErrorCode(1001002004, "当前状态无法完成策略执行");
    ErrorCode STRATEGY_EXECUTION_CANNOT_DELETE_WHEN_RUNNING = new ErrorCode(1001002005, "运行中的策略执行无法删除");

}
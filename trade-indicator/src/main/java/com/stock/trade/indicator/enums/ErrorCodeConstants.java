package com.stock.trade.indicator.enums;

import com.stock.trade.framework.common.exception.ErrorCode;

/**
 * Indicator 错误码枚举类
 *
 * indicator 系统，使用 1-004-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 指标定义 1-004-000-000 ========== //
    ErrorCode INDICATOR_DEFINITION_NOT_EXISTS = new ErrorCode(1_004_000_000, "指标定义不存在");
    ErrorCode INDICATOR_DEFINITION_NAME_EXISTS = new ErrorCode(1_004_000_001, "指标定义名称已存在");

    // ========== 指标计算结果 1-004-001-000 ========== //
    ErrorCode INDICATOR_VALUE_NOT_EXISTS = new ErrorCode(1_004_001_000, "指标计算结果不存在");

}
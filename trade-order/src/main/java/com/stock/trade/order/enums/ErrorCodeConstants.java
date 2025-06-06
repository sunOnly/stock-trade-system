package com.stock.trade.order.enums;

import com.stock.trade.common.exception.ErrorCode;

/**
 * trade-order 模块错误码枚举
 *
 * @author xxx
 */
public interface ErrorCodeConstants {

    // ========== 订单 1003001000 开头 ============
    ErrorCode ORDER_NOT_EXISTS = new ErrorCode(1003001000, "订单不存在");
    ErrorCode ORDER_CREATE_FAILED = new ErrorCode(1003001001, "订单创建失败");
    ErrorCode ORDER_UPDATE_FAILED = new ErrorCode(1003001002, "订单更新失败");
    ErrorCode ORDER_DELETE_FAILED = new ErrorCode(1003001003, "订单删除失败");
    ErrorCode ORDER_STATUS_INVALID = new ErrorCode(1003001004, "订单状态不合法");
    ErrorCode ORDER_PRICE_INVALID = new ErrorCode(1003001005, "订单价格不合法");
    ErrorCode ORDER_QUANTITY_INVALID = new ErrorCode(1003001006, "订单数量不合法");
    ErrorCode ORDER_TYPE_INVALID = new ErrorCode(1003001007, "订单类型不合法");
    ErrorCode ORDER_DIRECTION_INVALID = new ErrorCode(1003001008, "订单方向不合法");

    // ========== 订单项 1003002000 开头 ============
    ErrorCode ORDER_ITEM_NOT_EXISTS = new ErrorCode(1003002000, "订单项不存在");

    // ========== 订单日志 1003003000 开头 ============
    ErrorCode ORDER_LOG_NOT_EXISTS = new ErrorCode(1003003000, "订单日志不存在");

}
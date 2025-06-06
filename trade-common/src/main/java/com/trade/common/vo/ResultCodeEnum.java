package com.trade.common.vo;

import lombok.Getter;

/**
 * <p>结果代码枚举</p>
 *
 * @author creator
 * @since 2024-01-01
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"),
    OPERATION_FAILED(5001, "操作失败"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未经授权"),
    FORBIDDEN(403, "访问被拒绝"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
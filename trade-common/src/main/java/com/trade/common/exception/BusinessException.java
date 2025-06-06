package com.trade.common.exception;

import com.trade.common.vo.ResultCodeEnum;
import lombok.Getter;

/**
 * <p>自定义业务异常类</p>
 *
 * @author creator
 * @since 2024-01-01
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final int code;

    /**
     * 构造器，使用自定义消息
     *
     * @param message 异常消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = ResultCodeEnum.OPERATION_FAILED.getCode(); // 默认为操作失败
    }

    /**
     * 构造器，使用自定义错误码和消息
     *
     * @param code    错误码
     * @param message 异常消息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造器，使用 ResultCodeEnum 定义的错误
     *
     * @param resultCodeEnum 结果代码枚举
     */
    public BusinessException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    /**
     * 构造器，使用 ResultCodeEnum 定义的错误，并附加额外信息
     *
     * @param resultCodeEnum 结果代码枚举
     * @param detailMessage  详细错误信息，将追加到枚举定义的消息后
     */
    public BusinessException(ResultCodeEnum resultCodeEnum, String detailMessage) {
        super(resultCodeEnum.getMessage() + ": " + detailMessage);
        this.code = resultCodeEnum.getCode();
    }

    /**
     * 构造器，包装另一个异常
     *
     * @param message 异常消息
     * @param cause   原始异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(); // 默认为内部服务器错误
    }

    /**
     * 构造器，使用自定义错误码、消息并包装另一个异常
     *
     * @param code    错误码
     * @param message 异常消息
     * @param cause   原始异常
     */
    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * 构造器，使用 ResultCodeEnum 并包装另一个异常
     *
     * @param resultCodeEnum 结果代码枚举
     * @param cause          原始异常
     */
    public BusinessException(ResultCodeEnum resultCodeEnum, Throwable cause) {
        super(resultCodeEnum.getMessage(), cause);
        this.code = resultCodeEnum.getCode();
    }
}
package com.trade.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>通用API接口返回结果封装类</p>
 *
 * @author creator
 * @since 2024-01-01
 */
@Data
@Schema(description = "通用API接口返回结果")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    @Schema(description = "成功标志", example = "true")
    private boolean success = true;

    /**
     * 返回处理消息
     */
    @Schema(description = "返回处理消息", example = "操作成功！")
    private String message = "操作成功！";

    /**
     * 返回代码
     */
    @Schema(description = "返回代码", example = "200")
    private Integer code = 200;

    /**
     * 返回数据对象 data
     */
    @Schema(description = "返回数据对象")
    private T result;

    /**
     * 时间戳
     */
    @Schema(description = "时间戳", example = "1609459200000")
    private long timestamp = System.currentTimeMillis();

    public Result() {
    }

    public Result(boolean success, String message, Integer code) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> Result<T> ok() {
        Result<T> r = new Result<>();
        r.setSuccess(true);
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    public static <T> Result<T> ok(String msg) {
        Result<T> r = new Result<>();
        r.setSuccess(true);
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(msg);
        return r;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> r = new Result<>();
        r.setSuccess(true);
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        r.setResult(data);
        return r;
    }

    public static <T> Result<T> ok(String msg, T data) {
        Result<T> r = new Result<>();
        r.setSuccess(true);
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(msg);
        r.setResult(data);
        return r;
    }

    public static <T> Result<T> error(String msg) {
        return error(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> r = new Result<>();
        r.setSuccess(false);
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    public static <T> Result<T> error(ResultCodeEnum resultCodeEnum) {
        Result<T> r = new Result<>();
        r.setSuccess(false);
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return r;
    }

    public static <T> Result<T> error(ResultCodeEnum resultCodeEnum, String msg) {
        Result<T> r = new Result<>();
        r.setSuccess(false);
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(msg); // Use custom message
        return r;
    }

    /**
     * 无权限访问返回结果
     */
    public static <T> Result<T> noauth(String msg) {
        return error(ResultCodeEnum.UNAUTHORIZED.getCode(), msg);
    }
}
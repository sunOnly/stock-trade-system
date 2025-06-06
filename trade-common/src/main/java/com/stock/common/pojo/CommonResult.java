package com.stock.common.pojo;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * 通用返回结果
 *
 * @param <T> 数据泛型
 */
@Data
@Validated
public class CommonResult<T> implements Serializable {

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 错误提示
     */
    private String msg;

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(200); // 默认成功码为200
        result.setData(data);
        result.setMsg("成功");
        return result;
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(code);
        result.setMsg(message);
        return result;
    }

    // 可根据需要添加更多静态方法，例如处理特定错误码等
}
package com.trade.common.config;

import com.trade.common.exception.ApiException;
import com.trade.common.response.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @author Trade Team
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义API异常
     * @param e API异常
     * @return 统一结果封装
     */
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }
}
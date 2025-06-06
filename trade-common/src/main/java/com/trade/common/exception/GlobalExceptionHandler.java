package com.trade.common.exception;

import com.trade.common.vo.ResultCodeEnum;
import com.trade.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>全局异常处理器</p>
 * <p>捕获controller层抛出的异常，并返回统一的JSON格式响应</p>
 *
 * @author creator
 * @since 2024-01-01
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常 BusinessException
     *
     * @param e BusinessException 实例
     * @return Result 封装的错误响应
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK) // 通常业务异常返回200，通过code和message区分
    public Result<?> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常 (Query Param / Path Variable)
     *
     * @param e ConstraintViolationException 实例
     * @return Result 封装的错误响应
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream()
                .map(violation -> String.format("%s: %s", getFieldName(violation), violation.getMessage()))
                .collect(Collectors.joining("; "));
        log.warn("参数校验失败 (ConstraintViolationException): {}", message);
        return Result.error(ResultCodeEnum.BAD_REQUEST, message);
    }

    private String getFieldName(ConstraintViolation<?> violation) {
        String propertyPath = violation.getPropertyPath().toString();
        // propertyPath 通常是 "methodName.argName.fieldName" 或 "fieldName"
        // 我们尝试获取最后一个点之后的部分作为字段名
        int lastDotIndex = propertyPath.lastIndexOf('.');
        return (lastDotIndex == -1) ? propertyPath : propertyPath.substring(lastDotIndex + 1);
    }

    /**
     * 处理参数校验异常 (RequestBody @Valid)
     *
     * @param e MethodArgumentNotValidException 实例
     * @return Result 封装的错误响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining("; "));
        log.warn("参数校验失败 (MethodArgumentNotValidException): {}", message);
        return Result.error(ResultCodeEnum.BAD_REQUEST, message);
    }

    /**
     * 处理参数绑定异常 (form-data/x-www-form-urlencoded @Valid)
     *
     * @param e BindException 实例
     * @return Result 封装的错误响应
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining("; "));
        log.warn("参数绑定失败 (BindException): {}", message);
        return Result.error(ResultCodeEnum.BAD_REQUEST, message);
    }

    /**
     * 处理缺少请求参数异常
     *
     * @param e MissingServletRequestParameterException 实例
     * @return Result 封装的错误响应
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String message = String.format("缺少必要的请求参数: %s (类型: %s)", e.getParameterName(), e.getParameterType());
        log.warn(message);
        return Result.error(ResultCodeEnum.BAD_REQUEST, message);
    }

    /**
     * 处理参数类型不匹配异常
     *
     * @param e MethodArgumentTypeMismatchException 实例
     * @return Result 封装的错误响应
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = String.format("参数类型不匹配: 参数 '%s' 需要类型 '%s', 但提供了值 '%s'",
                e.getName(), e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "N/A", e.getValue());
        log.warn(message, e);
        return Result.error(ResultCodeEnum.BAD_REQUEST, message);
    }

    /**
     * 处理HTTP请求方法不支持异常
     *
     * @param e HttpRequestMethodNotSupportedException 实例
     * @return Result 封装的错误响应
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String message = String.format("不支持的请求方法: %s. 支持的方法有: %s", e.getMethod(), e.getSupportedHttpMethods());
        log.warn(message);
        return Result.error(ResultCodeEnum.FORBIDDEN, message);
    }

    /**
     * 处理404 NoHandlerFoundException
     * 需要在 application.properties 中配置 spring.mvc.throw-exception-if-no-handler-found=true
     * 和 spring.web.resources.add-mappings=false (如果使用了静态资源映射)
     *
     * @param e NoHandlerFoundException 实例
     * @return Result 封装的错误响应
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        String message = String.format("接口 [%s %s] 不存在", e.getHttpMethod(), e.getRequestURL());
        log.warn(message, e);
        return Result.error(ResultCodeEnum.NOT_FOUND, message);
    }

    /**
     * 处理请求体不可读异常
     *
     * @param e HttpMessageNotReadableException 实例
     * @return Result 封装的错误响应
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("请求体不可读或JSON格式错误: {}", e.getMessage());
        return Result.error(ResultCodeEnum.BAD_REQUEST, "请求体不可读或JSON格式错误");
    }

    /**
     * 处理其他所有未捕获的运行时异常
     *
     * @param e RuntimeException 实例
     * @return Result 封装的错误响应
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleRuntimeException(RuntimeException e) {
        log.error("服务器发生运行时异常: {}", e.getMessage(), e);
        return Result.error(ResultCodeEnum.INTERNAL_SERVER_ERROR, "服务器发生运行时异常，请联系管理员");
    }

    /**
     * 处理其他所有未捕获的顶级异常 Exception
     *
     * @param e Exception 实例
     * @return Result 封装的错误响应
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleException(Exception e) {
        log.error("服务器发生未知异常: {}", e.getMessage(), e);
        return Result.error(ResultCodeEnum.INTERNAL_SERVER_ERROR, "服务器发生未知异常，请联系管理员");
    }
}
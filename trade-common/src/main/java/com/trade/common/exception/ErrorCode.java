package com.trade.common.exception;

/**
 * 封装API的错误码
 * @author Trade Team
 */
public interface ErrorCode {
    long getCode();
    String getMessage();
}
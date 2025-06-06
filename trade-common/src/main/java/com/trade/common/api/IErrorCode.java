package com.trade.common.api;

/**
 * 封装API的错误码
 * @author Trade Team
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
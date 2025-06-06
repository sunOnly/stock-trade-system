package com.trade.marketdata.entity;

import lombok.Data;

/**
 * @author mac
 * @date 2024/7/16
 * @description Tushare API 请求实体类
 */
@Data
public class TushareRequest {

    /**
     * API 接口名称
     */
    private String apiName;

    /**
     * Tushare Token
     */
    private String token;

    /**
     * 请求参数
     */
    private Object params;

    /**
     * 返回字段
     */
    private String fields;
}
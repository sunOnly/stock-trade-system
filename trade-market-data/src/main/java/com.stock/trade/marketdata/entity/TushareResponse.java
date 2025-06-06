package com.trade.marketdata.entity;

import lombok.Data;

import java.util.List;

/**
 * @author mac
 * @date 2024/7/16
 * @description Tushare API 响应实体类
 */
@Data
public class TushareResponse {

    /**
     * 返回码，0 表示成功
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private TushareData data;

    @Data
    public static class TushareData {
        /**
         * 字段列表
         */
        private List<String> fields;
        /**
         * 数据列表
         */
        private List<List<Object>> items;
        /**
         * 总行数
         */
        private Integer has_more;
    }
}
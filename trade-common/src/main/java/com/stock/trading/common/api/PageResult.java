package com.stock.trading.common.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页数据封装类
 */
@Data
@NoArgsConstructor
public class PageResult<T> {
    private Long pageNum;
    private Long pageSize;
    private Long totalPage;
    private Long total;
    private List<T> list;

    /**
     * 将MyBatisPageHelper分页后的list转为分页信息
     */
    public static <T> PageResult<T> restPage(List<T> list) {
        PageResult<T> result = new PageResult<T>();
        //PageInfo<T> pageInfo = new PageInfo<T>(list);
        //result.setTotalPage(pageInfo.getPages());
        //result.setPageNum(pageInfo.getPageNum());
        //result.setPageSize(pageInfo.getPageSize());
        //result.setTotal(pageInfo.getTotal());
        //result.setList(pageInfo.getList());
        return result;
    }
}
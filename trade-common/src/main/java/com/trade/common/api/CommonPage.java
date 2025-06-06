package com.trade.common.api;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 分页数据封装类
 * @author Trade Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CommonPage<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> list;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<>();
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> pageInfo = page;
        result.setTotalPage((int)pageInfo.getPages());
        result.setPageNum((int)pageInfo.getCurrent());
        result.setPageSize((int)pageInfo.getSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getRecords());
        return result;
    }

    /**
     * 将MyBatis Plus分页后的Page转为分页信息
     */
    public static <T> CommonPage<T> restPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> page) {
        CommonPage<T> result = new CommonPage<>();
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> pageInfo = page;
        result.setTotalPage((int)pageInfo.getPages());
        result.setPageNum((int)pageInfo.getCurrent());
        result.setPageSize((int)pageInfo.getSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getRecords());
        return result;
    }
}
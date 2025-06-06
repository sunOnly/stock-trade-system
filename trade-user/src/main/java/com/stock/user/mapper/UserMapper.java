package com.stock.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.user.dal.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper
 *
 * @author TraeAI
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
    // TODO: 定义用户相关的数据库操作方法
}
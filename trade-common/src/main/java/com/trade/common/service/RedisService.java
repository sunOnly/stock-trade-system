package com.trade.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis操作Service, 
 * 对象和数组都以json形式进行存储
 * @author Trade Team
 */
public interface RedisService {
    /**
     * 存储数据
     */
    void set(String key, Object value);

    /**
     * 存储数据并设置过期时间
     */
    void set(String key, Object value, long expire);

    /**
     * 获取数据
     */
    Object get(String key);

    /**
     * 设置过期时间
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     */
    void del(String key);

    /**
     * 批量删除数据
     */
    void del(List<String> keys);

    /**
     * 批量删除key
     */
    Long delByPrefix(String prefix);

    /**
     * 对某个key的value进行递增操作
     */
    Long increment(String key, Long delta);

    /**
     * 对某个key的value进行递减操作
     */
    Long decrement(String key, Long delta);

    /**
     * 获取所有key
     */
    Set<String> keys(String pattern);

    /**
     * 判断key是否存在
     */
    Boolean hasKey(String key);

    /**
     * 将数据放入redis的list中
     */
    Long lPush(String key, Object value);

    /**
     * 将数据放入redis的list中并设置过期时间
     */
    Long lPush(String key, Object value, long expire);

    /**
     * 从redis的list中获取数据
     */
    Object lPop(String key);

    /**
     * 将数据放入redis的set中
     */
    Long sAdd(String key, Object... values);

    /**
     * 将数据放入redis的set中并设置过期时间
     */
    Long sAdd(String key, long expire, Object... values);

    /**
     * 从redis的set中获取数据
     */
    Set<Object> sMembers(String key);

    /**
     * 将数据放入redis的hash中
     */
    void hPut(String key, String hashKey, Object value);

    /**
     * 将数据放入redis的hash中并设置过期时间
     */
    void hPut(String key, String hashKey, Object value, long expire);

    /**
     * 从redis的hash中获取数据
     */
    Object hGet(String key, String hashKey);

    /**
     * 将map放入redis的hash中
     */
    void hPutAll(String key, Map<String, Object> map);

    /**
     * 将map放入redis的hash中并设置过期时间
     */
    void hPutAll(String key, Map<String, Object> map, long expire);

    /**
     * 从redis的hash中获取所有数据
     */
    Map<String, Object> hGetAll(String key);

    /**
     * 删除redis的hash中的数据
     */
    void hDel(String key, Object... hashKeys);
}
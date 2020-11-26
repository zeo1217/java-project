package com.qf.service;

import java.util.Set;

public interface ICarCacheService {
    /**
     * 添加购物车
     * @param key 用户唯一标识
     * @param gid 商品id
     * @param sum 商品数量
     */
    void put(String key,String gid,String sum);

    /**
     * 判断商品是否存在
     * @param key 用户标识
     * @param gid 商品id
     * @return
     */
    Boolean hasKey(String key,String gid);

    /**
     *  获取购物车
     * @param key 用户标识
     * @param gid 商品id
     * @return
     */
    String get(String key,String gid);

    /**
     *  删除购物车
     * @param key 用户标识
     * @param gid 商品id
     * @return
     */
    Long delete(String key,String gid);

    /**
     *  获取用户所有商品id
     * @param key 用户标识
     * @return
     */
    Set<String> keys(String key);
}

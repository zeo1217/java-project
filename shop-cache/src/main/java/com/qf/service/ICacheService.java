package com.qf.service;

public interface ICacheService {
    /**
     *
     * @param key
     * @param val
     * @param exp 超时时间。如果没有，设置为null
     * @throws Exception
     */
    public void set(String key,String val,Long exp)throws Exception;

    public void del(String key)throws Exception;

    public String get(String key)throws Exception;

    public void setnx(String key,String val,Long exp)throws Exception;

    String getOrderSequence();
}

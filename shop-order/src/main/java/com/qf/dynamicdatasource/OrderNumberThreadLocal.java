package com.qf.dynamicdatasource;

public class OrderNumberThreadLocal {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void set(String dbName){
        threadLocal.set(dbName);
    }

    public static String get(){
        return threadLocal.get();
    }
}

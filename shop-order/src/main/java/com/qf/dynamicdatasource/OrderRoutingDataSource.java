package com.qf.dynamicdatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

public class OrderRoutingDataSource extends AbstractRoutingDataSource {
    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        String s = OrderNumberThreadLocal.get();
        System.out.println("[OrderRoutingDataSource]-->dbNum:"+s);
        return s;
    }
}

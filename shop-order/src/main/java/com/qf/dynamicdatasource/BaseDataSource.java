package com.qf.dynamicdatasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;

@Data
public class BaseDataSource {

    private String url;

    private String username;

    private String password;

    private String  driverClassName;

    private String alias;

    public HikariDataSource getDataSource(){

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);

        return dataSource;
    }
}

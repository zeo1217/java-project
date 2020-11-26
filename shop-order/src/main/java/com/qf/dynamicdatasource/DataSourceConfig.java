package com.qf.dynamicdatasource;

import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Autowired
    private Order1DataSource order1DataSource;

    @Autowired
    private Order2DataSource order2DataSource;

    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocation;

    @Bean
    public OrderRoutingDataSource orderRoutingDataSource(){

        Map<Object,Object> map = new HashMap<>();
        map.put(order1DataSource.getAlias(),order1DataSource.getDataSource());
        map.put(order2DataSource.getAlias(),order2DataSource.getDataSource());

        OrderRoutingDataSource orderRoutingDataSource = new OrderRoutingDataSource();
        orderRoutingDataSource.setTargetDataSources(map);
        orderRoutingDataSource.setDefaultTargetDataSource(order1DataSource.getDataSource());
        return  orderRoutingDataSource;
    }

    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean (OrderRoutingDataSource orderRoutingDataSource){
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(orderRoutingDataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.qf.entity"); //设置别名
        try {
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactoryBean;
    }

}

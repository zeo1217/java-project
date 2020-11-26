package com.qf.dynamicdatasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix ="spring.datasource2" )
public class Order2DataSource extends BaseDataSource {
}

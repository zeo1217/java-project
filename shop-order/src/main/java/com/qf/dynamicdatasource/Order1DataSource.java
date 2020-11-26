package com.qf.dynamicdatasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix ="spring.datasource1" )
public class Order1DataSource extends BaseDataSource {
}

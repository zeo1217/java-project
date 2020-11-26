package com.qf.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Value("${es.host}")
    public String host;

    @Value("${es.port}")
    public int port;

    @Bean
    public RestHighLevelClient restHighLevelClient(){

        HttpHost httpHost = new HttpHost(host,port);

        RestClientBuilder builder = RestClient.builder(httpHost);

        RestHighLevelClient client = new RestHighLevelClient(builder);

        return  client;
    }
}

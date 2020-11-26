package com.qf.shopsearch;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ShopSearchApplicationTests {

    @Value("${es.index}")
    private String index;

    @Value("${es.type}")
    public String type;

    @Autowired
    private RestHighLevelClient client;

    @Test
    void contextLoads() throws Exception {

        CreateIndexRequest request=new CreateIndexRequest();
        request.index(index);

        // 创建settings
        Settings.Builder settings = Settings.builder();
        settings.put("number_of_shards", 3);
        settings.put("number_of_replicas", 1);

        //创建mapping
        XContentBuilder mapping = JsonXContent.contentBuilder()
                .startObject()
                    .startObject("properties")
                        .startObject("id")
                            .field("type", "integer")
                        .endObject()
                        .startObject("gname")
                            .field("type", "text")
                            .field("analyzer","ik_max_word") // gname和gdesc都需要分词
                        .endObject()
                        .startObject("gdesc")
                            .field("type", "text")
                            .field("analyzer","ik_max_word")
                        .endObject()
                        .startObject("gstock")
                            .field("type", "integer")
                        .endObject()
                        .startObject("gprice")
                            .field("type", "double")
                        .endObject()
                        .startObject("gpic")
                            .field("type", "keyword") // gpic不需要分词
                        .endObject()
                    .endObject()
                .endObject();
        request.settings(settings);
        request.mapping(type,mapping);

        CreateIndexResponse createIndexResponse = client.indices().create(request,RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.toString());
    }

}

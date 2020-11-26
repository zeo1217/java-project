package com.qf.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.qf.constants.EsConstants;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jettison.json.JSONString;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-08
 */
@Service
@Slf4j
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private RestHighLevelClient client;

    @Value("${es.index}")
    private String index;

    @Value("${es.type}")
    public String type;

    @Override
    public Boolean addGoods(Goods goods) throws Exception{
        //创建请求对象
        IndexRequest request = new IndexRequest(index,type,goods.getId().toString());
        //把goods转成json
        String jsonString = JSON.toJSONString(goods);
        //设置ES中插入的数据
        request.source(jsonString, XContentType.JSON);
        //发送请求
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        //获取结果
        if (EsConstants.CERATE_SUCCESS.equals(indexResponse.getResult().toString())){
            return true;
        }
        log.info("doc创建失败："+jsonString);
        return false;
    }

    @Override
    public Page<Goods> getGoodsPage(Integer current, Integer size)throws Exception {
        //创建请求条件
        SearchRequest request=new SearchRequest();
        request.indices(index).types(type);

        //设置查询条件
        SearchSourceBuilder builder=new SearchSourceBuilder();
        //设置分页
        builder.from((current-1)*size);
        builder.size(size);

        builder.query(QueryBuilders.matchAllQuery());

        //把builder放到request中
        request.source(builder);
        //发送请求
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);

        //从结果集中获取数据
        SearchHit[] hits = searchResponse.getHits().getHits();

        List<Goods> goodsList=new ArrayList<>();
        for (int i=0; i<hits.length;i++){
            //重ES中查询结果集，它是map
            Map<String, Object> sourceAsMap = hits[i].getSourceAsMap();
            //把map转对象
            Goods goods=new Goods();
            BeanUtils.populate(goods,sourceAsMap);
            //把对象添加集合中
            goodsList.add(goods);
        }

        //把所有的数据封装到page对象中
        Page<Goods> goodsPage=new Page<>();
        goodsPage.setCurrent(current);
        goodsPage.setSize(size);
        goodsPage.setRecords(goodsList);
        goodsPage.setTotal(searchResponse.getHits().getTotalHits());

        return goodsPage;
    }

    @Override
    public boolean deleteGoodsById(Integer id)throws Exception {
        return false;
    }

    @Override
    public List<Goods> searchGoods(String keyword, String[] searchFiled, String highlighter, String orderFiled, String orderRule) throws Exception{
        //创建请求对象
        SearchRequest request=new SearchRequest();
        request.indices(index).types(type);

        //创建查询条件
        SearchSourceBuilder builder=new SearchSourceBuilder();
        //设置查询条件
        if(keyword == null){
            builder.query(QueryBuilders.matchAllQuery()); // 如果没有指定查询条件，查询所有的商品
        }else{
            // 如果制定了查询条件，按照gname或者gdesc匹配
            builder.query(QueryBuilders.multiMatchQuery(keyword, searchFiled));
        }
        //设置高亮
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field(highlighter);
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.fragmentSize(50);

        builder.highlighter(highlightBuilder);

        //设置排序和排序规则
        if (orderFiled !=null){
            SortOrder sortOrder=SortOrder.DESC; //默认排序规则
            if (orderRule !=null &&  "asc".equalsIgnoreCase(orderRule)){
                sortOrder=SortOrder.ASC;
            }
            builder.sort(orderFiled,sortOrder);
        }

        //封装
        request.source(builder);

        //发送请求
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);

        //获取结果
        SearchHit[] hits = search.getHits().getHits();

        List<Goods> goodsList=new ArrayList<>();
        for (int i=0; i<hits.length; i++){
            //获取到一条数据
            Map<String, Object> sourceAsMap = hits[i].getSourceAsMap();

            //创建一个goods对象
            Goods goods=new Goods();

            //处理高亮结果
            Map<String, HighlightField> highlightFields = hits[i].getHighlightFields();
            if (highlightFields!=null && highlightFields.get(highlighter)!=null){
                //根据高亮字段，获取高亮内容
                Text[] fragments = highlightFields.get(highlighter).getFragments();
                String s = Arrays.toString(fragments);
                //覆盖之前没有高亮的内容
                sourceAsMap.put(highlighter,s);
            }
            //数据拷贝
            BeanUtils.populate(goods,sourceAsMap);
            //添加到集合
            goodsList.add(goods);
        }

        return goodsList;
    }
}

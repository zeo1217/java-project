package com.qf.service.impl;

import com.qf.entity.Goods;
import com.qf.listener.GoodsItemQueueListener;
import com.qf.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-12
 */
@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    private Configuration configuration;

    @Override
    public void createHtml(Goods goods) throws Exception{
        //准备数据
        Map<String,Object> map=new HashMap<>();
        map.put("gname",goods.getGname());
        map.put("gprice",goods.getGprice());
        map.put("gid",goods.getId());
        map.put("gpicList",goods.getGpic().split("\\|"));

        //准备模板
        Template template = configuration.getTemplate("goodsItem.ftl");
        //获取页面输出的目录
        String statiPath = GoodsItemQueueListener.class.getClassLoader().getResource("static").getPath();


        //准备视图输出位置
        FileWriter writer=null;

        //生成页面
        try {
            writer = new FileWriter(statiPath+ File.separator+goods.getId()+".html");
            template.process(map,writer);
        }catch (IOException e){
            log.error("生成静态页面出错");
        }finally {
            if (writer!=null){
                writer.close();
            }
        }
    }
}

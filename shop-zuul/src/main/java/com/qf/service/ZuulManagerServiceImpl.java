package com.qf.service;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-14
 */
@Service
public class ZuulManagerServiceImpl {

    Map<String,Object> map=new HashMap<>();
    {
        //0：需要效验
        //1：不需要效验
//        map.put("http://localhost/shop-back",1); //后台首页
//        map.put("http://localhost/shop-back/user/getUserPage",1); //后台用户显示列表
//        map.put("http://localhost/shop-back/user/addUser",1); //后台用户添加
        map.put("http://localhost/shop-order/order/toOrderConfrim",1);

        map.put("http://localhost/shop-home",0); //不用效验

    }

   public boolean isRequre(String serverPath){
        //根据server地址从map中查询
        Object o = map.get(serverPath);
        if (o!=null){
            if ("1".equals(o.toString())){
                return true; //需要token效验
            }
        }
        return false;
    }
}

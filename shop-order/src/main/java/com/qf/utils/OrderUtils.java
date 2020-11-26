package com.qf.utils;

import com.qf.feign.api.ICarCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.Subject;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-19
 */
@Component
public class OrderUtils {

    Integer dbNum = 2; // 两个库
    Integer tabNum = 2; // 两张表

    @Autowired
    private ICarCacheService iCarCacheService;

    public  String getOrderId(Integer uid){
        //规则：时间+用户id后四位+流水号
        StringBuffer ordBuffer=new StringBuffer();

        //时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());

        ordBuffer.append(date);
        //用户id后四位
        String subUid=subUserId(uid.toString());
         ordBuffer.append(subUid);

        //流水号
        String orderSequence = iCarCacheService.getOrderSequence();
        ordBuffer.append(orderSequence);

        return ordBuffer.toString();
    }


    private static String subUserId(String uid) {
        StringBuffer buffer=new StringBuffer();
        //如果用户id超过四位，取后四位
        //如果用户id不够四位，补0在id前面补
        if (uid.length()<4){
            //先添加之前的id
            buffer.append(uid);
            //循环补0
            for (int i=0; i<(4-uid.length());i++){
                buffer.insert(0,0);
            }
        }else {
            buffer.append(uid.substring(uid.length()-4));
        }

        return buffer.toString();
    }

    public Integer setOrderDataSource(Integer uid) {

        // 获取用户id后四位
        Integer subUserId = Integer.parseInt(subUserId(uid.toString()));

        // 计算数据库的编号
        Integer dbIndex = (subUserId%dbNum)+1;

        // 设置数据源
        //OrderNumberThreadLocal.set("db"+dbIndex);

        System.out.println("uid:"+uid);
        System.out.println("setOrderDataSource:db"+dbIndex);

        // 计算表的索引
        Integer tabIndex = (subUserId/tabNum%tabNum)+1;

        return tabIndex;
    }
}

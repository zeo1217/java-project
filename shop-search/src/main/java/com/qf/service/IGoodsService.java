package com.qf.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.qf.entity.Goods;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IGoodsService {
    /**
     * 添加到ES
     * @param goods 添加商品信息
     * @return
     */
    Boolean addGoods(Goods goods) throws Exception;

    /**
     *从ES中查询
     * @param current 当前页
     * @param size  每页显示条数
     * @return   分页对象
     */
    Page<Goods> getGoodsPage(Integer current,Integer size)throws Exception;

    /**
     *  根据id删除商品
     * @param id 被删除的商品id
     * @return
     */
    boolean deleteGoodsById(Integer id)throws Exception;


    /**
     * 按照条件查询
     * @param keyword 关键字
     * @param searchFiled 搜索字段
     * @param highlighter 高亮字段
     * @param orderFiled 排序字段
     * @param orderRule 排序规则
     * @return
     */
    List<Goods> searchGoods(String keyword, String[] searchFiled, String highlighter, String orderFiled, String orderRule) throws Exception;
}

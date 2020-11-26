package com.qf.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.qf.entity.Goods;
import com.qf.entity.ResultEntity;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-08
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService iGoodsService;

    @RequestMapping("/addGoods")
    @ResponseBody
    public ResultEntity addGoods(@RequestBody Goods goods) throws Exception {
        boolean addGoodsFlag= iGoodsService.addGoods(goods);
        return ResultEntity.response(addGoodsFlag);
    }

    @RequestMapping("/getGoodsPage")
    @ResponseBody
    public ResultEntity getGoodsPage(@RequestBody Page<Goods> page )throws Exception{
        Page<Goods> goodsPage = iGoodsService.getGoodsPage(page.getCurrent(),page.getSize());
        return ResultEntity.response(goodsPage);
    }

    @RequestMapping("/deleteGoodsById/{id}")
    @ResponseBody
    public ResultEntity deleteGoodsById(@PathVariable Integer id)throws Exception{
        boolean goodsById = iGoodsService.deleteGoodsById(id);
        return ResultEntity.response(goodsById);
    }

    //1.关键字搜索 --》keyword
    //2，按属性搜索，可以多个字段 --》searchFiled
    //3，关键字高亮 --》gname
    //4，排序字段，排序规则  --》orderFiled,orderRule
    @RequestMapping("/searchGoods")
    public String searchGoods(@RequestParam Map<String,String> map, Model model) throws Exception{

        //准备要查询的数据
        String keyword=null;
        String[] searchFiled="gname,gdesc".split(","); //可能有多个属性
        String highlighter="gname";
        String orderFiled=null; //没规则就按默认值
        String orderRule=null;

        //获取传递是数据
        if (!StringUtils.isEmpty(map.get("keyword"))){
            keyword=map.get("keyword");
        }

        if (!StringUtils.isEmpty(map.get("orderFiled"))){
            orderFiled=map.get("orderFiled");
        }

        if (!StringUtils.isEmpty(map.get("orderRule"))){
            orderRule=map.get("orderRule");
        }

        //查询ES
        List<Goods> goodsList= iGoodsService.searchGoods(keyword,searchFiled,highlighter,orderFiled,orderRule);
        model.addAttribute("goodsList",goodsList);
        return "searchGoodsList";
    }
}

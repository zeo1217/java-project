package com.qf.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.qf.entity.Goods;
import com.qf.entity.ResultEntity;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-05
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService iGoodsService;

    @RequestMapping("/addGoods")
    public ResultEntity addGoods(@RequestBody Goods goods){
        boolean insert = iGoodsService.insert(goods);
        return ResultEntity.response(goods);
    }

    @RequestMapping("/getGoodsPage")
    public ResultEntity getGoodsPage(@RequestBody Page<Goods> page){
        return ResultEntity.response(iGoodsService.selectPage(page));
    }

    @RequestMapping("/getGoodsById/{id}")
    public Goods getGoodsById(@PathVariable Integer id){
        return iGoodsService.selectById(id);
    }
}

package com.qf.controller;

import com.qf.entity.Goods;
import com.qf.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-12
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/createHtml")
    public String createHtml(@RequestBody Goods goods) throws Exception {
        itemService.createHtml(goods);
        return "ok";
    }
}

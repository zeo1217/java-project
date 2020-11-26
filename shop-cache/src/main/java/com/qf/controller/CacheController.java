package com.qf.controller;

import com.qf.entity.ResultEntity;
import com.qf.service.ICacheService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-14
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private ICacheService iCacheService;

    @RequestMapping("/set")
    public void set(String key,String val,Long exp) throws Exception {
         iCacheService.set(key,val,exp);
    }

    @RequestMapping("/get")
    public String get(String key) throws Exception {
        return iCacheService.get(key);
    }

    @RequestMapping("/del")
    public void del(String key) throws Exception {
        iCacheService.del(key);
    }

    @RequestMapping("/getOrderSequence")
    public String getOrderSequence(){
        return iCacheService.getOrderSequence();
    }
}

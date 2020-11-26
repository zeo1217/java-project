package com.qf.feign.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient("SHOP-CACHE")
public interface ICacheService {
    @RequestMapping("/cache/set")
    public void set(@RequestParam("key") String key,@RequestParam("val") String val,@RequestParam("exp") Long exp);

    @RequestMapping("/cache/get")
    public String get(@RequestParam("key") String key);

    @RequestMapping("/cache/del")
    public void del(@RequestParam("key") String key) ;

    @RequestMapping("/cache/getOrderSequence")
    public String getOrderSequence();
}

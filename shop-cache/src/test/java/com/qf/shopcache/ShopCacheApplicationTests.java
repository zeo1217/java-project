package com.qf.shopcache;

import com.netflix.discovery.converters.Auto;
import com.qf.service.ICacheService;
import com.qf.service.ICarCacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.ManagementServerPortUtils;

@SpringBootTest
class ShopCacheApplicationTests {

    @Autowired
    private ICacheService iCacheService;

    @Autowired
    private ICarCacheService iCarCacheService;

    @Test
    void contextLoads() throws Exception {
        iCacheService.set("a","b",null);
    }

    @Test
    void test1(){
        String token="user1";

        iCarCacheService.put(token,"1","2");
        iCarCacheService.put(token,"2","3");
        iCarCacheService.put(token,"3","4");

        Boolean aBoolean = iCarCacheService.hasKey(token, "1");
        System.out.println("aBoolean"+aBoolean);

        String s = iCarCacheService.get(token, "3");
        System.out.println("s："+s);

        Long delete = iCarCacheService.delete(token, "3");
        System.out.println("delete："+delete);

        String s2 = iCarCacheService.get(token, "3");
        System.out.println("s2："+s2);
    }

}

package com.qf.shopsso;

import com.qf.utils.DeCodeUtils;
import org.apache.commons.math.random.RandomDataImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class ShopSsoApplicationTests {

    //随机数
    @Test
    void contextLoads() {
        int code=new RandomDataImpl().nextInt(1000,9999);
        System.out.println(code);
    }

    //密码加密
    @Test
    void mima(){
        System.out.println(DeCodeUtils.hashpw("123"));
    }

}

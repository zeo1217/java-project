package com.qf.shoporder;


import com.qf.dynamicdatasource.Order1DataSource;
import com.qf.dynamicdatasource.Order2DataSource;
import com.qf.dynamicdatasource.OrderNumberThreadLocal;
import com.qf.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopOrderApplicationTests {

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private Order1DataSource order1DataSource;

    @Autowired
    private Order2DataSource order2DataSource;


    @Test
    public void test(){


        System.out.println(order1DataSource);
        System.out.println(order2DataSource);
    }



    @Test
    void contextLoads() throws Exception{
        OrderNumberThreadLocal.set("db1");

        String  integer= iOrderService.addOrder(16, 1);
        System.out.println(integer);
    }



}

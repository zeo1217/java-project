package com.qf.shopcar;

import com.qf.entity.Car;
import com.qf.service.ICarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopCarApplicationTests {

    @Autowired
    private ICarService iCarService;

    @Test
    void contextLoads() {
            Car car=new Car();
            car.setGid(10);
            car.setUid(20);
            car.setSum(3);
            iCarService.insert(car);
    }

}

package com.qf.controller;

import com.qf.entity.Car;
import com.qf.service.ICarCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-15
 */
@RestController
@RequestMapping("/carCache")
public class CarCacheController {
    @Autowired
    private ICarCacheService iCarCacheService;

    @RequestMapping("/addCar")
    public void addCar(String userToken,String gid,String sum){

        //添加之前先判断这个商品是否存在
        if (!iCarCacheService.hasKey(userToken,gid)){
            iCarCacheService.put(userToken,gid,sum);
        }else {
            //盖商品已经存在
            String redisSum = iCarCacheService.get(userToken, gid);
            Integer sum1=Integer.parseInt(redisSum);
            Integer sum2=Integer.parseInt(sum);
            sum=(sum1+sum2)+"";
            //覆盖
            iCarCacheService.put(userToken,gid,sum);
        }
    }

    @RequestMapping("/getUserCarList")
    public List<Car> getUserCarList(String userToken){
        List<Car> carList=new ArrayList<>();
        //根据一个key查询有那些filed
        Set<String> keys = iCarCacheService.keys(userToken);
        for (String gid : keys) {
            String sum = iCarCacheService.get(userToken, gid);
            Car car=new Car();
            car.setGid(Integer.parseInt(gid));
            car.setSum(Integer.parseInt(sum));
            carList.add(car);
        }
        return carList;
    }

    @RequestMapping("/deleteCar")
    public void deleteCar(String userToken,String gid){
        iCarCacheService.delete(userToken,gid);
    }


}

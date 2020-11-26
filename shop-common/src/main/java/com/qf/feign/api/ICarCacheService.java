package com.qf.feign.api;

import com.qf.entity.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("SHOP-CACHE")
public interface ICarCacheService extends ICacheService {
    @RequestMapping("/carCache/addCar")
    public void addCar(@RequestParam("userToken") String userToken, @RequestParam("gid") String gid, @RequestParam("sum") String sum);

    @RequestMapping("/carCache/getUserCarList")
    public List<Car> getUserCarList(@RequestParam("userToken") String userToken);

    @RequestMapping("/carCache/deleteCar")
    public void deleteCar(@RequestParam("userToken") String userToken, @RequestParam("gid") String gid);
}

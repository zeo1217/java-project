package com.qf.feign.api;

import com.qf.entity.Car;
import com.qf.entity.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@FeignClient("SHOP-CAR")
public interface ICarMySQLService {
    @RequestMapping("/car/batchAddCar")
    public ResultEntity batchAddCar(@RequestBody List<Car> carList);

    @RequestMapping("/car/getCarListByUserId/{uid}")
    public Map<String,Object> getCarListByUserId(@PathVariable("uid") Integer uid);

    @RequestMapping("/car/deleteCarByUid/{uid}")
    public ResultEntity deleteCarByUid(@PathVariable("uid") Integer uid);
}

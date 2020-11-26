package com.qf.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qf.aop.annocation.LoginToken;
import com.qf.constants.CacheConstants;
import com.qf.entity.Car;
import com.qf.entity.Goods;
import com.qf.entity.ResultEntity;
import com.qf.entity.User;
import com.qf.feign.api.ICarCacheService;
import com.qf.feign.api.IGoodsService;
import com.qf.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-15
 */
@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private ICarCacheService iCarCacheService; //操作redis

    @Autowired
    private ICarService iCarService; //操作数据库

    @Autowired
    private IGoodsService iGoodsService;

    @RequestMapping("/deleteCarByUid/{uid}")
    @ResponseBody
    public ResultEntity deleteCarByUid(@PathVariable Integer uid){
        System.out.println("删除id："+uid);
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("uid",uid);
        return ResultEntity.response(iCarService.delete(entityWrapper));
    }

    @RequestMapping("/batchAddCar")
    @ResponseBody
    public ResultEntity batchAddCar( @RequestBody List<Car> carList){
        return ResultEntity.response(iCarService.insertBatch(carList));
    }

    @RequestMapping("/addCar")
    @LoginToken
    @ResponseBody
    public ResultEntity addCar(User user, @CookieValue(name = CacheConstants.CAR_TOKEN ,required = false)String carToken,
                               Car car,
                               HttpServletResponse httpServletResponse){
        if (user==null){
            //把购物车添加到缓存
            if (StringUtils.isEmpty(carToken)) {

                // 创建cartoken
                carToken = UUID.randomUUID().toString();

                // 创建cookie
                Cookie cookie = new Cookie(CacheConstants.CAR_TOKEN, carToken);
                cookie.setPath("/");
                cookie.setMaxAge(60*60*24*7);

                httpServletResponse.addCookie(cookie);
            }
            System.out.println("添加到redis缓存中");
            iCarCacheService.addCar(carToken,car.getGid().toString(),car.getSum().toString());
            System.out.println("carToken值为："+carToken);
        }else{
            car.setUid(user.getId());

            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("gid", car.getGid());
            entityWrapper.eq("uid", car.getUid());
            Car dbCar = iCarService.selectOne(entityWrapper);
            if (dbCar == null) {
                iCarService.insert(car);
            } else {
                dbCar.setSum(car.getSum() + dbCar.getSum());
                iCarService.updateById(dbCar);
            }
        }
        return ResultEntity.success();
    }

    @RequestMapping("/getUserCarList")
    @LoginToken
    public String getUserCarList(User user,@CookieValue(name = CacheConstants.CAR_TOKEN,required = false) String carToken,
                                Model model){
        List<Car> carList = null;
        if (user == null) {
            if (!StringUtils.isEmpty(carToken)) {
                carList = iCarCacheService.getUserCarList(carToken);
            }
        } else {
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("uid", user.getId());
            carList = iCarService.selectList(entityWrapper); // 只能查询当前用户的
        }

        BigDecimal totalPrice =findCarGoodsInfo(carList);

        model.addAttribute("carList", carList);
        model.addAttribute("totalPrice", totalPrice);
        return "carList";
    }

    private BigDecimal findCarGoodsInfo(List<Car> carList) {
        // 查询购物车中的具体的商品信息
        BigDecimal totalPrice = new BigDecimal(0);
        if (carList != null) {
            for (Car car : carList) {
                Goods goods = iGoodsService.getGoodsById(car.getGid());

                BigDecimal gprice = goods.getGprice();
                Integer sum = car.getSum();

                // 计算小计
                car.setSubTotal(gprice.multiply(BigDecimal.valueOf(sum)));
                car.setGoods(goods);

                totalPrice =totalPrice.add(car.getSubTotal());
            }
        }
        return totalPrice;
    }

    @RequestMapping("/deleteCar/{gid}")
    @LoginToken
    @ResponseBody
    public ResultEntity deleteCar(User user,
                          @CookieValue(name = CacheConstants.CAR_TOKEN,required = false) String carToken,
                         @PathVariable("gid") String gid){
        if (user==null){
            iCarCacheService.deleteCar(carToken,gid);
        }else {
            EntityWrapper entityWrapper=new EntityWrapper();
            entityWrapper.eq("gid",gid);
            entityWrapper.eq("uid",user.getId());
            iCarService.delete(entityWrapper);
        }
        return ResultEntity.success();
    }


    @RequestMapping("/getCarListByUserId/{uid}")
    @ResponseBody
    public Map<String,Object> getCarListByUserId(@PathVariable Integer uid){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("uid", uid);
        List list = iCarService.selectList(entityWrapper);
        BigDecimal totalPrice =findCarGoodsInfo(list);
        Map<String,Object> map = new HashMap<>();
        map.put("carList",list);
        map.put("totalPrice",totalPrice);
        return map; // 只能查询当前用户的
    }

}

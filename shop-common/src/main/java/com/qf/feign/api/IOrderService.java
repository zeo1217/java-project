package com.qf.feign.api;

import com.qf.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("SHOP-ORDER")
public interface IOrderService {

    @RequestMapping("/order/getOrderById/{uid}/{orderId}")
    public Order getOrderById(@PathVariable("uid") Integer uid, @PathVariable("orderId") String orderId);

    @RequestMapping("/order/updateOrderStatus")
    public void updateOrderStatus(@RequestParam("orderId") String orderId, @RequestParam("status") String status);
}

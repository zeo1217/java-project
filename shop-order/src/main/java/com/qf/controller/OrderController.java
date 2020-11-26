package com.qf.controller;

import com.qf.aop.annocation.LoginToken;
import com.qf.entity.Address;
import com.qf.entity.Car;
import com.qf.entity.Order;
import com.qf.entity.User;
import com.qf.feign.api.IAddressService;
import com.qf.feign.api.ICarMySQLService;
import com.qf.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-17
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IAddressService iAddressService;

    @Autowired
    private ICarMySQLService iCarMySQLService;

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("/toOrderConfrim")
    @LoginToken
    public String toOrderConfrim(User user, Model model){

        //先根据用户id查询地址信息
        List<Address> addressList = iAddressService.getAddressListByUid(user.getId());
        System.out.println(addressList);

        //用户的购物车
        Map<String, Object> map = iCarMySQLService.getCarListByUserId(user.getId());
        System.out.println(map);

        Object carList = map.get("carList");
        Object totalPrice = map.get("totalPrice");

        model.addAttribute("carList",carList);
        model.addAttribute("addressList",addressList);
        model.addAttribute("totalPrice",totalPrice);
        System.out.println(map);
        return "orderConfrim";
    }


    @RequestMapping("/addOrder")
    @LoginToken
    // @ResponseBody
    public String addOrder(User user,Integer addressId)throws Exception{
        System.out.println("开始添加");
        //添加订单
        String orderId= iOrderService.addOrder(user.getId(), addressId);
        //跳转到支付平台
        System.out.println(orderId);
        return "redirect:http://localhost:8103/pay/alipay?orderId="+orderId+"&userId="+user.getId();
    }

    @RequestMapping("/getOrderById/{uid}/{orderId}")
    @ResponseBody
    public Order getOrderById(@PathVariable Integer uid, @PathVariable String orderId){
        Order order = iOrderService.getOrderById(uid,orderId);
        return order;
    }

    @RequestMapping("/updateOrderStatus")
    @ResponseBody
    public void updateOrderStatus(String orderId,String status){
        iOrderService.updateOrderStatus(orderId,status);
    }

    @RequestMapping("/getOrderListByUid")
    @LoginToken
    public String getOrderListByUid(User user,Model model){
        List<Order> orderList =iOrderService.getOrderListByUid(user.getId());
        model.addAttribute("orderList",orderList);
        return "orderList";
    }

}

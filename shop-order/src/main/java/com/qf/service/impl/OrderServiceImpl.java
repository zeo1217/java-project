package com.qf.service.impl;

import com.qf.entity.Address;
import com.qf.entity.Car;
import com.qf.entity.Order;
import com.qf.entity.OrderDetail;
import com.qf.feign.api.IAddressService;
import com.qf.feign.api.ICarMySQLService;
import com.qf.mapper.IOrderDetailMapper;
import com.qf.mapper.IOrderMapper;
import com.qf.service.IOrderDetailService;
import com.qf.service.IOrderService;
import com.qf.utils.OrderUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-18
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IAddressService iAddressService;

    @Autowired
    private ICarMySQLService iCarMySQLService;

    @Autowired
    private OrderUtils orderUtils;

    @Autowired
    private IOrderMapper iOrderMapper;

    @Autowired
    private IOrderDetailMapper iOrderDetailMapper;

    @Override
    public String addOrder(Integer uid, Integer addressId) throws Exception {
        //根据地址id查询地址信息
        Address address = iAddressService.getAddressById(addressId);
        //根据用户id查询购物车信息
        Map<String, Object> carMap = iCarMySQLService.getCarListByUserId(uid);
        String totalPriceStr = carMap.get("totalPrice").toString();
        BigDecimal totalPrice=new BigDecimal(totalPriceStr);

        //生成订单id
        String orderId= orderUtils.getOrderId(uid);

        //封装订单对象
        Order order=new Order();
        order.setId(orderId); //系统自动生成
        order.setUid(uid);
        order.setStatus(1);
        order.setTotalPrice(totalPrice);
        order.setAddress(address.getAddress());
        order.setName(address.getName());
        order.setMobile(address.getMobile());
        order.setCreateDate(new Date());

        // 设置数据源，返回表的编号
        Integer tabIndex = orderUtils.setOrderDataSource(uid);

        //插入订单
        iOrderMapper.addOrder(order,tabIndex);
        log.info(order.toString());

        //插入订单详情
        //先获取所有的购物车
        List<Map<String,Object>> carList = (List<Map<String, Object>>) carMap.get("carList");

        // 创建一个集合，用来装所有的订单详情
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (Map<String, Object> car: carList) {

            // 获取购车的信息
            Integer gid = Integer.parseInt(car.get("gid").toString());
            Integer sum = Integer.parseInt(car.get("sum").toString());
            BigDecimal subTotal = new BigDecimal(car.get("subTotal").toString());

            // 获取商品的信息
            Map<String,Object> goodsMap= (Map<String,Object>)car.get("goods"); // goods
            OrderDetail orderDetail = new OrderDetail();
            // 把map中的数据拷贝到orderDetail
            BeanUtils.populate(orderDetail,goodsMap);

            // 手动赋值其他属性
            orderDetail.setGsum(sum);
            orderDetail.setGid(gid);
            orderDetail.setOid(orderId);
            orderDetail.setSubTotal(subTotal);
            orderDetail.setId(null); // 上面把商品的id拷贝进来了
            orderDetailList.add(orderDetail);

        }

        iOrderDetailMapper.batchAddOrderDetail(orderDetailList,tabIndex);
        //iOrderDetailMapper.batchAddOrderDetail(orderDetailList,tabIndex);

        //清空购物车
        iCarMySQLService.deleteCarByUid(uid);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer uid, String orderId) {
        //设置数据源，返回order表的索引
        Integer tabId = orderUtils.setOrderDataSource(uid);

        //查询订单
        Order order = iOrderMapper.getOrderById(orderId,tabId);

        return order;
    }

    @Override
    public void updateOrderStatus(String orderId, String status) {

        //设置数据源
        String uid = orderId.substring(8, 8 + 4);
        Integer tabIndex = orderUtils.setOrderDataSource(Integer.parseInt(uid));

        //更新
        iOrderMapper.updateOrderStatus(orderId,status,tabIndex);
    }

    @Override
    public List<Order> getOrderListByUid(Integer uid) {
        Integer tabIndex = orderUtils.setOrderDataSource(uid);
        List<Order> orderList = iOrderMapper.getOrderListByUid(uid,tabIndex);
        return orderList;
    }
}

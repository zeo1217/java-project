package com.qf.service;


import com.qf.entity.Order;

import java.util.List;

public interface IOrderService {

    String addOrder(Integer uid ,Integer addressId) throws Exception;

    Order getOrderById(Integer uid, String orderId);

    void updateOrderStatus(String orderId, String status);

    List<Order> getOrderListByUid(Integer uid);
}

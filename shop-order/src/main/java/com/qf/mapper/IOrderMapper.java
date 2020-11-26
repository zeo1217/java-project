package com.qf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qf.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrderMapper extends BaseMapper<Order> {
    int addOrder(@Param("order") Order order, @Param("tabIndex") Integer tabIndex);

    Order getOrderById(@Param("orderId") String orderId, @Param("tabIndex") Integer tabIndex);

    void updateOrderStatus(@Param("orderId") String orderId, @Param("status") String status, @Param("tabIndex") Integer tabIndex);

    List<Order> getOrderListByUid(@Param("userId") Integer userId, @Param("tabIndex") Integer tabIndex);
}

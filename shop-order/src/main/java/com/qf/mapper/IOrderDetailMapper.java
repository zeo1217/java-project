package com.qf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qf.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrderDetailMapper extends BaseMapper<OrderDetail> {


    void batchAddOrderDetail(@Param("list") List<OrderDetail> orderDetails, @Param("tabIndex") Integer tabIndex);
}

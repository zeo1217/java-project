package com.qf.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qf.entity.OrderDetail;
import com.qf.mapper.IOrderDetailMapper;
import com.qf.service.IOrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<IOrderDetailMapper,OrderDetail> implements IOrderDetailService {
}

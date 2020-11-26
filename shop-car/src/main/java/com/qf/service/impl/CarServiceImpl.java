package com.qf.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qf.entity.Car;
import com.qf.mapper.CarMapper;
import com.qf.service.ICarService;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-15
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements ICarService {
}

package com.qf.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qf.entity.Goods;
import com.qf.mapper.IGoodsMapper;
import com.qf.service.IGoodsService;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-05
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<IGoodsMapper,Goods> implements IGoodsService{
}

package com.qf.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qf.entity.User;
import com.qf.mapper.IUserMapper;
import com.qf.service.IUservice;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-03
 */
@Service
public class UserServiecImpl extends ServiceImpl<IUserMapper, User> implements IUservice {
}

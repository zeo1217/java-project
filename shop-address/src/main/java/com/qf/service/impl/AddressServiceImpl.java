package com.qf.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qf.entity.Address;
import com.qf.mapper.IAddressMapper;
import com.qf.service.IAddressService;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-17
 */
@Service
public class AddressServiceImpl extends ServiceImpl<IAddressMapper, Address> implements IAddressService {
    @Override
    public Integer insertAddress(Address address) {
        return super.baseMapper.insertAddress(address);
    }
}

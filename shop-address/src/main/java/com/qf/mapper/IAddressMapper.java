package com.qf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qf.entity.Address;

public interface IAddressMapper extends BaseMapper<Address> {
    Integer insertAddress(Address address);
}

package com.qf.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qf.aop.annocation.LoginToken;
import com.qf.entity.Address;
import com.qf.entity.ResultEntity;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-17
 */
@RestController
@RequestMapping("/address")
@Slf4j
public class AddressController {

    @Autowired
    private IAddressService iAddressService;

    @RequestMapping("/getAddressListByUid/{uid}")
    public List<Address> getAddressListByUid(@PathVariable Integer uid){
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("uid",uid);
        List list = iAddressService.selectList(entityWrapper);
        log.info(list.size()+"");
        return list;
    }


    @RequestMapping("/insertAddress")
    @LoginToken
    public ResultEntity insertAddress(User user,Address address){
        log.info(address.toString());
        address.setUid(user.getId());
        return ResultEntity.success(iAddressService.insertAddress(address));
    }

    @RequestMapping("/getAddressById/{aid}")
    public Address getAddressById(@PathVariable Integer aid){
        return iAddressService.selectById(aid);
    }
}

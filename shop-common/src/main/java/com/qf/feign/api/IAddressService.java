package com.qf.feign.api;

import com.qf.entity.Address;
import com.qf.entity.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("SHOP-ADDRESS")
public interface IAddressService {

    @RequestMapping("/address/getAddressListByUid/{uid}")
    public List<Address> getAddressListByUid(@PathVariable("uid") Integer uid);

    @RequestMapping("/address/getAddressById/{aid}")
    public Address getAddressById(@PathVariable("aid") Integer aid);
}

package com.qf.feign.api;

import com.qf.entity.ResultEntity;
import com.qf.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-12
 */
@FeignClient("SHOP-USER")
public interface IUserService {
    @RequestMapping("/user/checkUser")
    public User  checkUser(@RequestBody Map<String, Object> map);

    @RequestMapping("/user/addUser")
    public ResultEntity addUser(@RequestBody User user);

    @RequestMapping("/user/getUserByUsername")
    public User getUserByUsername(@RequestParam("username") String username);

    @RequestMapping("/user/updatePassword")
    void updatePassword(@RequestParam("hashpw")String hashpw,@RequestParam("id") Integer id);
}

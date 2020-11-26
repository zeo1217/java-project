package com.qf.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qf.entity.ResultEntity;
import com.qf.entity.User;
import com.qf.service.IUservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-03
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private IUservice iUservice;

    @RequestMapping("/getUserByUsername")
    public User getUserByUsername(String username){
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("username",username);
        User user = iUservice.selectOne(entityWrapper);
        return user;
    }

    @RequestMapping("/checkUser")
    public User checkUser(@RequestBody Map<String,String> map){
        EntityWrapper entityWrapper = new EntityWrapper();

        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            entityWrapper.eq(entry.getKey(),entry.getValue());
        }

        User user = iUservice.selectOne(entityWrapper);

        log.info("user:"+user);
        return user;
    }

    @RequestMapping("/login")
    public ResultEntity login(String username, String password){
        //设置条件
        EntityWrapper entityWrapper=new EntityWrapper();
        entityWrapper.eq("username",username);
        entityWrapper.eq("password",password);
        //查询
        User user = iUservice.selectOne(entityWrapper);

        //判断
        if (user!=null){
            return ResultEntity.success(user);
        }else {
            return ResultEntity.error("用户名或密码错误！");
        }
    }

    @RequestMapping("/getUserPage")
    public ResultEntity getUserPage(@RequestBody Page<User> page){
        Page<User> userPage = iUservice.selectPage(page);
        return ResultEntity.response(userPage);
    }

    @RequestMapping("/addUser")
    public ResultEntity addUser(@RequestBody User user){
        boolean insert = iUservice.insert(user);
        return ResultEntity.response(insert);
    }

    @RequestMapping("/deleteUser/{id}")
    public ResultEntity deleteUser(@PathVariable Integer id){
        return ResultEntity.response(iUservice.deleteById(id));
    }



    @RequestMapping("/updatePassword")
    public void updatePassword(String hashpw,Integer id){
        User user = new User();
        user.setId(id);
        user.setPassword(hashpw);
        iUservice.updateById(user);

    }


}

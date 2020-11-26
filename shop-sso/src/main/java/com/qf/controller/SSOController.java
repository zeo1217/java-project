package com.qf.controller;

import com.qf.aop.annocation.LoginToken;
import com.qf.constants.CacheConstants;
import com.qf.entity.Car;
import com.qf.entity.Email;
import com.qf.entity.ResultEntity;
import com.qf.entity.User;
import com.qf.feign.api.ICacheService;
import com.qf.feign.api.ICarCacheService;
import com.qf.feign.api.ICarMySQLService;
import com.qf.feign.api.IUserService;
import com.qf.utils.DeCodeUtils;
import com.qf.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math.random.RandomDataImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-12
 */
@RestController
@RequestMapping("/sso")
@Slf4j
public class SSOController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ICarCacheService iCasheService;

    @Autowired
    private ICarMySQLService iCarMySQLService;

    @Value("${emailChange}")
    private String exchange;


    @RequestMapping("/checkUsername")
    public Map<String,String> checkUsername(String username){

        Map<String, String> map = new HashMap<>();
            map.put("status", "n");
            map.put("info", "该用户名已被注册");

        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("username",username);
        User user = iUserService.checkUser(paramMap);
        if (user==null) {
            map.put("status", "y");
            map.put("info", "可以注册");

        }
        return map;
    }


    @RequestMapping("/inputUsername")
    public Map<String, String> inputUsername(String param) {

        Map<String, String> map = new HashMap<>();
        map.put("status", "n"); //
        map.put("info", "用户不存在");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", param);
        User user = iUserService.checkUser(paramMap);
        log.info("user:"+user);
        if (user != null) {
            map.put("status", "y");
            map.put("info", "用户存在");
        }
        return map;
    }

    @RequestMapping("/sendEmail")
    public ResultEntity sendEmail(String email){


        if (!StringUtils.isEmpty(email)) {

            // 1.验证这个email是否已经被注册了
            Map<String, Object> map = new HashMap<>();
            map.put("email", email);

            // 2.查询邮箱是否已经被注册
            User user= iUserService.checkUser(map); // 如果存在返回的true

            // 3.如果返回的success，说明邮箱没有被注册
            if (user == null) {

                // 生成验证码
                int code = new RandomDataImpl().nextInt(1000, 9999);

                // 保存到reids
                // key=email_token，避免了其他业务模块也使用email作为key
                iCasheService.set(email + CacheConstants.REGISTER_TOKEN, code + "", 15L);

                // 4.封装邮件的信息
                Email email1 = new Email();
                email1.setSubject("商城注册");
                email1.setContent("验证码:" + code);
                email1.setTo(email);
                System.out.println(email1.toString());
                // 5.发送邮件
                rabbitTemplate.convertAndSend(exchange, "email.send", email1);

                // 6.邮件发送成功
                return ResultEntity.success();
            } else {
                // 返回邮箱已经被注册
                return ResultEntity.error("邮箱已被注册");
            }
        }
        return ResultEntity.error("邮箱为空");
    }

    @RequestMapping("/registerUser")
    public ResultEntity registerUser(User user,String code){
        log.debug(code);
        log.debug(user.toString());

        //判断验证码是否正确
        //先根据用户的邮箱查询到验证码
        String redisCode = iCasheService.get(user.getEmail() + CacheConstants.REGISTER_TOKEN);
        //判断验证码是否为空
        if (StringUtils.isEmpty(redisCode)){
             return ResultEntity.error("验证码已过期，请重新发送");
        }
        //判断用输入的验证码跟系统生成的验证码是否一致
        if (!redisCode.equals(code)){
            return ResultEntity.error("验证码错误！");

        }

        //对密码加密
        String hashpw= DeCodeUtils.hashpw(user.getPassword());
        user.setPassword(hashpw);

        //注册
        ResultEntity resultEntity = iUserService.addUser(user);
        if (ResultEntity.SUCEESS.equals(resultEntity.getStatus())){
            return ResultEntity.success("注册成功");
        }else {
            return ResultEntity.error("注册失败");
        }

    }

    @RequestMapping("/login")
    public ResultEntity login(String username, String password,
                              @CookieValue(name = CacheConstants.CAR_TOKEN ,required = false)String carToken,
                              HttpServletResponse response){
        //根据用户名查询用户
        User user = iUserService.getUserByUsername(username);
        if (user==null){
            return ResultEntity.error("用户不存在");
        }
        //密码错误
        if (!DeCodeUtils.checkpw(password,user.getPassword())){
            return ResultEntity.error("用户名或密码错误！");
        }

        //生成一个token
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("id", user.getId().toString());
        tokenMap.put("username", user.getUsername().toString());
        String token = JWTUtils.createToken(tokenMap, null);

        //合并购物车，将redis里的数据同步到数据库
        System.out.println("carToken的值为:"+carToken);
        if (!StringUtils.isEmpty(carToken)){
            List<Car> userCarList = iCasheService.getUserCarList(carToken);
            if (!userCarList.isEmpty()){

                //同步到数据库中
                for (Car car : userCarList) {
                        car.setUid(user.getId()); //设置uid
                }

                //把购物车添加到MySQL
                iCarMySQLService.batchAddCar(userCarList);

                //删除用户在redis中的购物车
                iCasheService.del(carToken);

                //删除cookie
                Cookie cookie=new Cookie(CacheConstants.CAR_TOKEN,"");
                cookie.setMaxAge(-1);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        //响应数据
        return ResultEntity.success(token);
    }

    //设置生产数据类型，及编码
    @RequestMapping(value = "/sendEmailChangePassoword",produces = "text/html;charset=utf-8")
    public String sendEmailChangePassoword(String username){
        //根据用户名查询邮箱
        Map<String,Object> map=new HashMap<>();
        map.put("username",username);
        User user = iUserService.checkUser(map);

        //判断
        if (user!=null){
            //获取邮箱
            String email = user.getEmail();

            Map<String,String> tokenMap=new HashMap<>();
            tokenMap.put("id",user.getId().toString());
            String token = JWTUtils.createToken(tokenMap,60*30); //生存时间30秒

            //封装邮件对象
            Email email1=new Email();
            email1.setSubject("2002-shop用户密码修改");
            email1.setContent("<h1><a href='http://localhost/shop-sso/sso/toChangePasswordPage?token="+token+"'>点击这里修改密码</a></h1>");
            email1.setTo(email);

            // 发送邮件
            rabbitTemplate.convertAndSend(exchange,"email.send",email1);
            //响应用户
            String fix = email.substring(email.lastIndexOf("@")+1);
            String mailUrl="http://mail."+fix;
            return "修改密码的链接以发送您的邮箱，点击<a href='"+mailUrl+"'>这里</a>登录邮箱查看";
        }
        return null;
    }

    @RequestMapping("/updatePassword")
    public ResultEntity updatePassword(String password,String token){
        //验证token
        ResultEntity resultEntity= JWTUtils.require(token);
        if (ResultEntity.SUCEESS.equals(resultEntity.getStatus())){
            //从token中获取userid
            String id = JWTUtils.getClaim(token, "id");
            //修改密码,先加密
            String hashpw = DeCodeUtils.hashpw(password);
            iUserService.updatePassword(hashpw,Integer.parseInt(id));
            //提示用户修改成功
            return ResultEntity.success("修改成功");
        }else {
            return ResultEntity.error(resultEntity.getMsg());
        }

    }

    @LoginToken
    @RequestMapping("/isLogin")
    public ResultEntity isLogin(User user){
        System.out.println("对象信息为："+user.toString());
        if(user != null){
            return ResultEntity.success(user);
        }else{
            return ResultEntity.error("没有登录");
        }
    }

    @RequestMapping("/logout")
    public ResultEntity logout(){
        log.info("logout");
        return null;
    }
}

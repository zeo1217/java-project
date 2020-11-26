package com.qf.aop.impl;

import com.qf.aop.annocation.LoginToken;
import com.qf.entity.ResultEntity;
import com.qf.entity.User;
import com.qf.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class LoginTokenAOP {

    @Around("@annotation(loginToken)")
    public Object tokenHandler(ProceedingJoinPoint point, LoginToken loginToken) throws Throwable{

        // 1.获取servlet相关对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        // 2.获取token
        String token = request.getHeader("Authorization");
        if(StringUtils.isEmpty(token)){
            token = request.getParameter("token");
        }
        log.info(token);

        // 3.判断用户是否有token
        User user = null;
        if (!StringUtils.isEmpty(token)) {
            // 说明用户已经登录了
            ResultEntity resultEntity = JWTUtils.require(token);
            if (ResultEntity.SUCEESS.equals(resultEntity.getStatus())) {
                // 表单token验证成功
                String id = JWTUtils.getClaim(token, "id");
                String username = JWTUtils.getClaim(token, "username");
                user = new User();
                user.setUsername(username);
                user.setId(Integer.parseInt(id));
            }
        }

        Object[] args = point.getArgs();

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i]; // ????
            if(arg != null){
                if ((arg.getClass().equals(User.class))) { // 5.2 遍历找到user的参数
                    args[i] = user; // 替换原来的值
                    break;
                }
            }
        }
       return point.proceed(args);
    }
}

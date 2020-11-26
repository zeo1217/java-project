package com.qf.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qf.entity.ResultEntity;
import com.sun.org.apache.xml.internal.security.exceptions.AlgorithmAlreadyRegisteredException;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-14
 */
@Slf4j
public class JWTUtils {

    static String sign="2002-qf-shop";

    /**
     * 获取token
     * @param map
     * @return
     */
    public static String createToken(Map<String,String> map,Integer exp){
        JWTCreator.Builder builder = JWT.create();

        // 主体
        builder.withSubject("2002-shop");

        // 2.设置用户的数据
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,60*60*24*5); // 5天

        if(exp !=null){
            instance.add(Calendar.SECOND,exp); // 指定时间
        }
        builder.withExpiresAt(instance.getTime());

        String token = builder.sign(Algorithm.HMAC256(JWTUtils.sign));// 签名

        return token;
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static ResultEntity require(String token){
        JWTVerifier build = JWT.require(Algorithm.HMAC256(JWTUtils.sign)).build();
        try {
            build.verify(token);
            return ResultEntity.success();
        }catch (TokenExpiredException e){
            log .warn("token已过期");
            return ResultEntity.error("token已过期");
        }catch (SignatureVerificationException e){
            log.warn("签名算法不一致");
            return ResultEntity.error("签名算法不一致");
        }catch (AlgorithmMismatchException e){
            log.warn("payload数据被修改");
            return ResultEntity.error("payload数据被修改");
        }catch (Exception e){
            log.warn("认证失败");
            return ResultEntity.error("认证失败");
        }
    }

    /**
     * 获取token中的数据
     * @param token
     * @param key
     * @return
     */
    public static String getClaim(String token,String key){
        JWTVerifier build = JWT.require(Algorithm.HMAC256(JWTUtils.sign)).build();
        DecodedJWT verify = build.verify(token);
        return verify.getClaim(key).asString();
    }
}

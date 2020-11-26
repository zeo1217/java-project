package com.qf.utils;


import org.mindrot.jbcrypt.BCrypt;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-14
 * 密码加密，及比对工具类
 */
public class DeCodeUtils {
    /**
     * 对密码的加密
     * @param pw
     * @return
     */
    public static String hashpw(String pw){
        return BCrypt.hashpw(pw,BCrypt.gensalt());
    }

    /**
     * 密码比对
     * @param pw 用户输入
     * @param dbPw  数据库查询的
     * @return
     */
    public static boolean checkpw(String pw,String dbPw){
        return BCrypt.checkpw(pw,dbPw);
    }
}

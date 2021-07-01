package com.lv.fast.common.util;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.MD5;

/**
 * 密码工具类
 * @author jie.lv
 */
public class PasswordUtil {

    private PasswordUtil(){}

    /**
     * 随机生成密码
     * @return 密码
     */
    public static String randomPassword(){
        return randomPassword(8);
    }

    /**
     * 随机生成密码
     * @param length 密码长度
     * @return 密码
     */
    public static String randomPassword(int length){
        return RandomUtil.random(length);
    }

    /**
     * 获取保存到数据库的密码
     * @param password 密码
     * @return 密码
     */
    public static String getSaveToDataBasePassword(String password){
        return BCrypt.hashpw(MD5.create().digestHex(password));
    }
}

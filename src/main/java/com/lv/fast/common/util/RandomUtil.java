package com.lv.fast.common.util;

import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * 生成随机值工具类
 * @author jie.lv
 */
public class RandomUtil {

    private RandomUtil(){}


    /**
     * 获取随机值源内容
     * @return 源
     */
    public static ArrayList<String> getSource(){
        ArrayList<String> source = Lists.newArrayListWithCapacity(62);
        //添加数字
        for (int i = 48; i < 58 ; i++) {
            source.add(String.valueOf((char)i));
        }
        //添加大写字母
        for (int i = 65; i < 91 ; i++) {
            source.add(String.valueOf((char)i));
        }
        //添加小写字母
        for (int i = 97; i < 123 ; i++) {
            source.add(String.valueOf((char)i));
        }
        return source;
    }

    /**
     * 生成随机字符串
     * @param length 长度
     * @return 字符串
     */
    public static String random(int length){
        String sourceString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        return cn.hutool.core.util.RandomUtil.randomString(sourceString, length);
    }
}

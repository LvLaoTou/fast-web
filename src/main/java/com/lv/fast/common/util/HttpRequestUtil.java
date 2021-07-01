package com.lv.fast.common.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lv.fast.common.constant.HttpConstant;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpRequest工具类
 * @author jie.lv
 */
public class HttpRequestUtil {

    private HttpRequestUtil(){}

    /**
     * 获取请求ip
     * @param request 请求对象
     * @return ip地址
     */
    public static String getRequestIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 从请求头获取token
     * @param request 请求对象
     * @return token
     */
    public static String getToken(HttpServletRequest request){
        String token = request.getHeader(HttpConstant.TOKEN_HEADER_KEY_SYS);
        if (StringUtils.isBlank(token)){
            token = request.getParameter(HttpConstant.TOKEN_HEADER_KEY_SYS);
        }
        return token;
    }
}

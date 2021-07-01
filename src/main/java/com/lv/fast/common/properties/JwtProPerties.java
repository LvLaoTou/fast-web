package com.lv.fast.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Jwt配置属性类
 * @author jie.lv
 */
@Component
public class JwtProPerties {

    /** 加密密钥 */
    public static String secret;

    /** 有效时长 */
    public static long validDuration;

    @Value("${my.jwt.secret}")
    public void setSecret(String secret) {
        JwtProPerties.secret = secret;
    }

    @Value("${my.jwt.validDuration}")
    public void setValidDuration(long validDuration) {
        JwtProPerties.validDuration = validDuration;
    }
}

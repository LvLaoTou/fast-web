package com.lv.fast.util;

import cn.hutool.core.codec.Base64;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lv.fast.constant.UserConstant;
import com.lv.fast.constant.JsonConstant;
import com.lv.fast.enums.RestResultEnum;
import com.lv.fast.properties.JwtProPerties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;

/**
 * Jwt工具类
 * @author jie.lv
 */
@Slf4j
public class JwtUtil {

    private JwtUtil(){}


    /**
     * 生成token
     * @param userId 用户对象
     * @return token
     */
    @SneakyThrows
    public static String generateToken(String userId){
        JWTCreator.Builder builder = JWT.create();
        //封装信息
        builder.withClaim(UserConstant.JSON_KEY, userId);
        // 设置JWT令牌的过期时间
        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(JwtProPerties.validDuration);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        builder.withExpiresAt(Date.from(zonedDateTime.toInstant()));
        // 设置签名并返回token
        return builder.sign(Algorithm.HMAC256(JwtProPerties.secret));
    }

    /**
     * 校验token是否有效
     * @param token token
     * @return 是否有效
     */
    public static boolean verify(String token) {
        DecodedJWT decodedJWT = decoded(token);
        return verify(decodedJWT);
    }

    /**
     * 校验token是否有效
     * @param decodedJWT 解密后的Jwt
     * @return 是否有效
     */
    public static boolean verify(DecodedJWT decodedJWT) {
        if (decodedJWT == null){
            return false;
        }
        Date expiresAt = decodedJWT.getExpiresAt();
        ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault());
        Date now = Date.from(zonedDateTime.toInstant());
        return expiresAt.after(now);
    }

    /**
     * 解密token
     * @param token token
     * @return 解密后的Jwt
     */
    public static DecodedJWT decoded(String token){
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(JwtProPerties.secret)).build().verify(token);
            return decodedJWT;
        }catch (AlgorithmMismatchException e){
            return null;
        }catch (Exception e){
            log.error("校验token异常", e);
            return null;
        }
    }

    /**
     * 从token中获取用户对象
     * @param token token
     * @return 用户Id
     */
    @SneakyThrows
    public static String getTokenUserId(String token){
        DecodedJWT decodedJwt = decoded(token);
        if (verify(decodedJwt)){
            String userId = decodedJwt.getClaim(UserConstant.JSON_KEY).asString();
            return userId;
        }
        return null;
    }

    @SneakyThrows
    private static String getUserIdByToken(String token){
        Assert.assertNotNull(token, RestResultEnum.TOKEN_IS_NULL);
        String[] split = token.split("\\.");
        String tokenBase64 = split[1];
        String decodeStr = Base64.decodeStr(tokenBase64);
        HashMap hashMap = JsonConstant.objectMapper.readValue(decodeStr, HashMap.class);
        Object userIdObj = hashMap.get(UserConstant.JSON_KEY);
        Assert.assertNotNull(userIdObj, RestResultEnum.TOKEN_INVALID);
        return userIdObj.toString();
    }

}

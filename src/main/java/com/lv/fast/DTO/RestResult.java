package com.lv.fast.DTO;

import com.google.common.collect.Maps;
import com.lv.fast.common.Describe;
import com.lv.fast.enums.RestResultEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @Description 统一响应对象
 * @Author jie.lv
 */
@Data
public class RestResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 状态标识码 */
    private String code;

    /** 描述信息 */
    private String message;

    /** 数据 */
    private T data;

    public RestResult(String code, String message, T data) {
        this.code = code;
        this.setMessage(message);
        this.data = data;
    }

    public RestResult(String code, String message) {
        this.code = code;
        this.setMessage(message);
    }

    public static <T> RestResult<T> build(String code, String message, T data){
        return new RestResult<>(code, message, data);
    }

    public static <T> RestResult<T> build(Describe describe, T data){
        return build(describe.getCode(), describe.getDescribe(), data);
    }

    public static RestResult build(Describe describe){
        return build(describe.getCode(), describe.getDescribe(), null);
    }

    public static RestResult build(boolean isSuccess){
        return isSuccess ? success() : error();
    }

    public static <T> RestResult<T> success(T data){
        return build(RestResultEnum.SUCCESS, data);
    }

    public static <T> RestResult<T> success(){
        return build(RestResultEnum.SUCCESS, null);
    }

    public static RestResult<HashMap<String, Object>> success(String key, Object value){
        HashMap<String, Object> result = Maps.newHashMapWithExpectedSize(1);
        result.put(key, value);
        return success(result);
    }

    public static <T> RestResult<T> error(){
        return build(RestResultEnum.UNKNOWN_ERROR, null);
    }

    public RestResult<T> withCode(String code){
        this.code = code;
        return this;
    }

    public RestResult<T> withMessage(String message){
        this.message = message;
        return this;
    }

    public RestResult<T> withData(T data){
        this.data = data;
        return this;
    }
}

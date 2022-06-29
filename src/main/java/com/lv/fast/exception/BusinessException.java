package com.lv.fast.exception;

import com.lv.fast.common.entity.Describe;
import com.lv.fast.common.constant.RestResultCodeConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义异常
 * @author lvlaotou
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,reason = "发生自定义异常")
public class BusinessException extends RuntimeException implements Describe {

    /**
     * 状态码
     */
    private String code;

    /**
     * 描述信息
     */
    private String describe;

    /**
     * 无参构造
     */
    public BusinessException(){}

    /**
     * 使用自定义异常枚举类构造自定义异常
     * @param describe 统一响应接口
     */
    public BusinessException(Describe describe){
        super(describe.getDescribe());
        this.code = describe.getCode().toString();
        this.describe = describe.getDescribe();
    }

    public BusinessException(String code, String describe){
        super(describe);
        this.code = code;
        this.describe = describe;
    }

    public BusinessException(String describe){
        super(describe);
        this.code = RestResultCodeConstant.PARAM_ERROR;
        this.describe = describe;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescribe() {
        return describe;
    }
}

package com.lv.fast.exception;

import com.lv.fast.common.entity.Describe;
import com.lv.fast.common.constant.RestResultCodeConstant;
import com.lv.fast.common.constant.RestResultDescribeConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义异常
 * @author lvlaotou
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,reason = "发生自定义异常")
public class BusinessException extends RuntimeException implements Describe<String> {

    /**
     * 状态码
     */
    private String code;

    /**
     * 描述信息
     */
    private String describe;

    /**
     * 无参构造，默认未知错误，避免 code 为 null 导致统一响应构建 NPE
     */
    @SuppressWarnings("unused")
    public BusinessException(){
        this.code = RestResultCodeConstant.UNKNOWN;
        this.describe = RestResultDescribeConstant.UNKNOWN;
    }

    /**
     * 使用自定义异常枚举类构造自定义异常
     * @param describe 统一响应接口
     */
    @SuppressWarnings("unused")
    public BusinessException(Describe<String> describe){
        super(describe.getDescribe());
        this.code = describe.getCode();
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

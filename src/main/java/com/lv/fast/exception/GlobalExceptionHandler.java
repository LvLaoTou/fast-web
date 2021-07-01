package com.lv.fast.exception;

import com.lv.fast.common.RestResult;
import com.lv.fast.common.enums.RestResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理
 * @author lv
 * @version 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截自定义异常
     * @param e 异常信息
     * @return R对象
     */
    @ExceptionHandler(MyException.class)
    public RestResult handleMethodMyException(MyException e) {
        log.error("发生自定义异常", e);
        return RestResult.build(e);
    }

    /**
     * 拦截参数校验异常
     * @param e 异常信息
     * @return R对象
     */
    @ExceptionHandler(BindException.class)
    public RestResult handleMethodArgumentNotValidException(BindException e) {
        log.error("请求参数异常", e);
        String message = "参数错误";
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (fieldError != null){
            message += ":"+fieldError.getDefaultMessage();
        }
        return RestResult.build(RestResultEnum.PARAM_ERROR).withMessage(message);
    }

    /**
     * 数据库记录已存在错误
     * @param e 异常对象
     * @return R对象
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public RestResult handleDuplicateKeyException(DuplicateKeyException e){
        log.error("数据库异常", e);
        return RestResult.build(RestResultEnum.DATABASE_EXIST_ERROR);
    }

    /**
     * 拦截shiro权限异常
     * @param e 异常信息
     * @return R对象
     */
    /*@ExceptionHandler(AuthorizationException.class)
    public RestResult handleAuthorizationException(AuthorizationException e){
        log.error("权限异常", e);
        return R.build(RestResultEnum.AUTHORIZATION_INSUFFICIENT_ERROR);
    }*/

    /**
     * 拦截路径错误
     * @param e 异常信息
     * @return R对象
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public RestResult handlerNoFoundException(Exception e) {
        log.error("路径异常，没有对应的处理器", e);
        return RestResult.build(RestResultEnum.PATH_NOT_FOUND);
    }

    /**
     * 拦截所有异常
     * @param e 异常信息
     * @return R对象
     */
    @ExceptionHandler(Exception.class)
    public RestResult handleException(Exception e){
        log.error("未知异常", e);
        return RestResult.error();
    }
}

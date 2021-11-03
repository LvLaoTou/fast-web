package com.lv.fast.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lv.fast.common.entity.RestResult;
import com.lv.fast.common.enums.RestResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.ExecutionException;

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
    public RestResult handle(MyException e) {
        log.error("发生自定义异常", e);
        return RestResult.build(e);
    }

    /**
     * 拦截多线程异常
     * @param e 多线程异常
     * @return
     */
    @ExceptionHandler(UndeclaredThrowableException.class)
    public RestResult handle(UndeclaredThrowableException e) {
        Throwable cause = e.getCause();
        if (cause instanceof ExecutionException){
            return handle((ExecutionException) cause);
        }
        log.error("多线程异常", e);
        return RestResult.error();
    }

    /**
     * 拦截多线程异常
     * @param e 多线程异常
     * @return
     */
    @ExceptionHandler(ExecutionException.class)
    public RestResult handle(ExecutionException e) {
        Throwable cause = e.getCause();
        if (cause instanceof MyException){
            return handle((MyException) cause);
        }
        if (cause instanceof JWTVerificationException){
            return handle((JWTVerificationException) cause);
        }
        if (cause instanceof  BindException){
            return handle((BindException) cause);
        }
        if (cause instanceof  DuplicateKeyException){
            return handle((DuplicateKeyException) cause);
        }
        if (cause instanceof  NoHandlerFoundException){
            return handle((NoHandlerFoundException) cause);
        }
        log.error("多线程异常", e);
        return RestResult.error();
    }

    /**
     * 拦截参数校验异常
     * @param e 异常信息
     * @return R对象
     */
    @ExceptionHandler(BindException.class)
    public RestResult handle(BindException e) {
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
    public RestResult handle(DuplicateKeyException e){
        log.error("数据库异常", e);
        return RestResult.build(RestResultEnum.DATABASE_EXIST_ERROR);
    }


    /**
     * 拦截路径错误
     * @param e 异常信息
     * @return R对象
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public RestResult handle(NoHandlerFoundException e) {
        log.error("路径异常，没有对应的处理器", e);
        return RestResult.build(RestResultEnum.PATH_NOT_FOUND);
    }

    /**
     * 拦截所有异常
     * @param e 异常信息
     * @return R对象
     */
    @ExceptionHandler(Exception.class)
    public RestResult handle(Exception e){
        log.error("未知异常", e);
        return RestResult.error();
    }
}

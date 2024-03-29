package com.lv.fast.exception;

import cn.hutool.core.util.StrUtil;
import com.lv.fast.common.entity.RestResult;
import com.lv.fast.common.enums.RestResultEnum;
import jakarta.validation.ConstraintViolationException;
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
 * @author lvlaotou
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public RestResult<?> handle(BusinessException e) {
        log.error("发生自定义异常", e);
        return RestResult.build(e);
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    public RestResult<?> handle(UndeclaredThrowableException e) {
        return handle(e.getCause());
    }

    @ExceptionHandler(ExecutionException.class)
    public RestResult<?> handle(ExecutionException e) {
        return handle(e.getCause());
    }

    @ExceptionHandler(BindException.class)
    public RestResult<?> handle(BindException e) {
        log.error("请求参数异常", e);
        String message = "参数错误";
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (fieldError != null){
            String field = fieldError.getField();
            message += ",参数名:"+field;
            String defaultMessage = fieldError.getDefaultMessage();
            if (defaultMessage != null && defaultMessage.contains("nested exception")) {
                defaultMessage = "类型错误";
            }
            message += ",错误描述:"+defaultMessage;
        }
        return RestResult.build(RestResultEnum.PARAM_ERROR).withMessage(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public RestResult<?> handle(ConstraintViolationException e) {
        log.error("请求参数异常", e);
        String message = e.getMessage();
        if (StrUtil.isNotBlank(message)){
            message = message.substring(message.lastIndexOf(":")+1).trim();
        }
        return RestResult.build(RestResultEnum.PARAM_ERROR).withMessage(message);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public RestResult<?> handle(DuplicateKeyException e){
        log.error("数据库异常", e);
        return RestResult.build(RestResultEnum.DATABASE_EXIST_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public RestResult<?> handle(NoHandlerFoundException e) {
        log.error("路径异常，没有对应的处理器", e);
        return RestResult.build(RestResultEnum.PATH_NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public RestResult<?> handle(Exception e){
        log.error("未知异常", e);
        Throwable cause = e.getCause();
        if (cause != null){
            while (true){
                Throwable sunCause = cause.getCause();
                if (sunCause == null){
                    return handle(cause);
                }
                cause = sunCause;
            }
        }
        return RestResult.error();
    }

    public RestResult<?> handle(Throwable throwable){
        if (throwable instanceof ExecutionException exception){
            return handle(exception);
        }
        if (throwable instanceof UndeclaredThrowableException exception){
            return handle(exception);
        }
        if (throwable instanceof BusinessException exception){
            return handle(exception);
        }
        if (throwable instanceof BindException exception){
            return handle(exception);
        }
        if (throwable instanceof DuplicateKeyException exception){
            return handle(exception);
        }
        log.error("发生异常", throwable);
        return RestResult.error();
    }
}

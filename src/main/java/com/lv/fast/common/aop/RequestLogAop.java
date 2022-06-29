package com.lv.fast.common.aop;

import com.lv.fast.common.util.HttpRequestUtil;
import com.lv.fast.common.util.JsonUtil;
import com.lv.fast.common.util.ParameterUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求日志aop
 * @author lvlaotou
 */
@Order
@Slf4j(topic = "RequestLog")
@Aspect
@Component
@RequiredArgsConstructor
public class RequestLogAop {

    private final HttpServletRequest request;

    /** 拦截所有controller */
    @Pointcut(value = "execution(* com.lv.fast..*.controller..*.*(..))")
    public void around() {}

    @SneakyThrows
    @Around("around()")
    public Object doAround(ProceedingJoinPoint pjp){
        boolean isSuccess = true;
        Object result = null;
        StopWatch stopWatch = new StopWatch();
        try{
            stopWatch.start("执行任务");
            result = pjp.proceed();
            return result;
        }catch (Throwable throwable){
            isSuccess = false;
            throw throwable;
        }finally {
            try {
                stopWatch.stop();
                stopWatch.start("记录请求信息");
                String requestParamJson = ParameterUtil.getRequestParamJson(pjp);
                log.debug("---------------------------------------------记录请求信息开始---------------------------------------------");
                log.debug("请求URI路径：{}", request.getRequestURI());
                log.debug("客户端IP地址：{}", HttpRequestUtil.getRequestIp(request));
                log.debug("请求参数：{}", requestParamJson);
                log.debug("请求响应：{}", result == null ? "" : (result instanceof MultipartFile) ? "响应内容" : JsonUtil.toJson(result));
                log.debug("是否执行成功：{}", isSuccess);
                stopWatch.stop();
                log.debug("耗时详情：");
                log.debug("     总耗时：{}ms", stopWatch.getTotalTimeMillis());
                log.debug("     --------------------------");
                log.debug("     ms         %     Task name");
                log.debug("     --------------------------");
                StopWatch.TaskInfo[] taskInfo = stopWatch.getTaskInfo();
                for (StopWatch.TaskInfo task : taskInfo) {
                    log.debug("     {}        {}       {}", task.getTimeMillis(), String.format("%.0f", (double) task.getTimeNanos() * 100 / stopWatch.getTotalTimeNanos()), task.getTaskName());
                }
                log.debug("---------------------------------------------记录请求信息结束---------------------------------------------");
                log.debug("                                                                                                       ");
            } catch (Exception e) {
                log.error("记录请求操作日志异常", e);
            }
        }
    }
}

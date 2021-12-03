package com.lv.fast.common.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程工具类
 * @author jie.lv
 */
@Slf4j
public class ThreadUtil {

    private ThreadUtil(){}

    public static final int MAXIMUM_POOL_SIZE = 20;

    public static final int CORE_POOL_SIZE = 5;

    public static final long KEEP_ALIVE_TIME = 30;

    public static final String LOG_THREAD_NAME_PREFIX = "fast_web_log_%d";

    /**
     * 日志线程池
     */
    public static final ThreadPoolExecutor LOG_THREAD_POOL_EXECUTOR =
            new ThreadPoolExecutor(CORE_POOL_SIZE,
                    MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE_TIME,
                    TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(5),
                    new ThreadFactoryBuilder()
                            .setUncaughtExceptionHandler((t,e)->{
                                log.error("日志子线程{}发生异常", t.getName() ,e);
                            })
                            .setNameFormat(LOG_THREAD_NAME_PREFIX)
                            .build(),
                    new ThreadPoolExecutor.CallerRunsPolicy());
}

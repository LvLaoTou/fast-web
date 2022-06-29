package com.lv.fast.common.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lvlaotou
 */
@Configuration
public class LogRecordConfig {

    @Bean
    @ConditionalOnMissingBean(LogRecordService.class)
    public LogRecordService logRecordService(){
        return new DefaultLogRecordServiceImpl();
    }
}

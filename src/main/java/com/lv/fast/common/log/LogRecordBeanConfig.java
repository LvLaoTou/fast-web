package com.lv.fast.common.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jie.lv
 */
@Configuration
public class LogRecordBeanConfig {

    @Bean
    @ConditionalOnMissingBean(LogRecordService.class)
    public LogRecordService logRecordService(){
        return new DefaultLogRecordService();
    }
}

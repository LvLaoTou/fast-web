package com.lv.fast.common.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

/**
 * @author jie.lv
 */
@Slf4j
@Service
@ConditionalOnMissingBean(LogRecordService.class)
public class DefaultLogRecordService implements LogRecordService{

    @Override
    public void record(Record record) {
        log.debug("业务日志:{}", record);
    }
}

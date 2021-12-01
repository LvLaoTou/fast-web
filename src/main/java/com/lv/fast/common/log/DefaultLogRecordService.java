package com.lv.fast.common.log;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jie.lv
 */
@Slf4j
public class DefaultLogRecordService implements LogRecordService{

    @Override
    public void record(Record record) {
        log.debug("业务日志:{}", record);
    }
}

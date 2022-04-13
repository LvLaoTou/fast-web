package com.lv.fast.module.test.service;

import com.lv.fast.common.log.LogRecord;
import com.lv.fast.common.log.LogRecordContext;
import com.lv.fast.common.log.OperateTypeEnum;
import com.lv.fast.module.test.dto.TestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author jie.lv
 */
@Service
@RequiredArgsConstructor
public class TestService {

    private final TestService2 testService2;

    @LogRecord(success = "'请求id:'+#request.id+',请求姓名:'+#request.name+'方法名:'+#methodName+'请求名称:'+#requestName",
            operateType = OperateTypeEnum.UPDATE,
            bizNo = "T(java.util.UUID).randomUUID()",
            fail = "'执行获取请求姓名失败'",
            condition = "#request.success")
    public String convert(TestRequest request){
        LogRecordContext.putVariable("methodName", "convert");
        testService2.test(request);
        LogRecordContext.putVariable("requestName", "convert");
        return request.getName();
    }
}

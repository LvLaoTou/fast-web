package com.lv.fast.module.test.service;

import com.lv.fast.common.aop.AopContext;
import com.lv.fast.common.log.LogRecord;
import com.lv.fast.common.log.LogRecordConstant;
import com.lv.fast.common.log.OperateTypeEnum;
import com.lv.fast.module.test.dto.TestRequest;
import org.springframework.stereotype.Service;

/**
 * @author lvlaotou
 */
@Service
public class TestService2 {

    @LogRecord(success = "'请求id:'+#request.id+',请求姓名:'+#request.name+'方法名:'+#methodName+'请求名称:'+#requestName",
            operateType = OperateTypeEnum.SELECT,
            bizNo = "T(java.util.UUID).randomUUID()",
            fail = "'执行嵌套,错误信息:'+#"+ LogRecordConstant.ERROR_MESSAGE_EVALUATION,
            condition = "#request.success")
    public long test(TestRequest request){
        AopContext.putVariable("methodName", "test");
        AopContext.putVariable("requestName", "testRequest");
        return request.getId();
    }
}

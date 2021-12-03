package com.lv.fast.model.test.service;

import com.lv.fast.common.log.LogRecord;
import com.lv.fast.common.log.LogRecordConstant;
import com.lv.fast.common.log.LogRecordContext;
import com.lv.fast.common.log.OperateTypeEnum;
import com.lv.fast.model.test.dto.TestRequest;
import org.springframework.stereotype.Service;

/**
 * @author jie.lv
 */
@Service
public class TestService2 {

    @LogRecord(success = "'请求id:'+#request.id+',请求姓名:'+#request.name+'方法名:'+#methodName+'请求名称:'+#requestName",
            operateType = OperateTypeEnum.SELECT,
            bizNo = "T(java.util.UUID).randomUUID()",
            fail = "'执行嵌套,错误信息:'+#"+ LogRecordConstant.ERROR_MESSAGE_EVALUATION,
            condition = "#request.success")
    public long test(TestRequest request){
        LogRecordContext.putVariable("methodName", "test");
        LogRecordContext.putVariable("requestName", "testRequest");
        int i = 1 / 0;
        return request.getId();
    }
}

package com.lv.fast.model.test.service;

import com.lv.fast.common.log.LogRecord;
import com.lv.fast.common.log.OperateTypeEnum;
import com.lv.fast.model.test.dto.TestRequest;
import org.springframework.stereotype.Service;

/**
 * @author jie.lv
 */
@Service
public class TestService {

    @LogRecord(success = "'请求id:'+#request.id+',请求姓名:'+#request.name",
            operateType = OperateTypeEnum.SELECT,
            bizNo = "T(java.util.UUID).randomUUID()",
            fail = "执行获取请求姓名失败",
            condition = "#request.success")
    public String convert(TestRequest request){
        return request.getName();
    }
}

package com.lv.fast.model.test.service;

import com.lv.fast.common.log.Operator;
import com.lv.fast.common.log.OperatorService;
import org.springframework.stereotype.Service;

/**
 * @author jie.lv
 */
@Service
public class TestOperatorServiceImpl implements OperatorService {

    @Override
    public Operator getOperator() {
        return Operator.builder().id("1").name("admin").build();
    }
}

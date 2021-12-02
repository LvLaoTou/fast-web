package com.lv.fast.model.test.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.lv.fast.common.entity.RestResult;
import com.lv.fast.model.test.dto.TestRequest;
import com.lv.fast.model.test.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jie.lv
 */
@Api(tags = "测试模块")
@ApiSupport(author = "lv")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/post")
    @ApiOperation(value = "测试post请求")
    @ApiOperationSupport(order = 1)
    public RestResult test(@RequestBody @Validated TestRequest request){
        return RestResult.success(testService.convert(request));
    }
}

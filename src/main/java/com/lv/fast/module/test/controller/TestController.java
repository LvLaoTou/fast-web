package com.lv.fast.module.test.controller;

import com.lv.fast.common.entity.RestResult;
import com.lv.fast.module.test.dto.TestRequest;
import com.lv.fast.module.test.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jie.lv
 */
@Tag(name = "测试模块")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/post")
    @Operation(summary = "测试post请求", description = "测试post请求")
    public RestResult test(@RequestBody @Validated TestRequest request){
        return RestResult.success(testService.convert(request));
    }
}

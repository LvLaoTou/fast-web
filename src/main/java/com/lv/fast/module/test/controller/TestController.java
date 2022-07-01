package com.lv.fast.module.test.controller;

import com.lv.fast.common.entity.RestResult;
import com.lv.fast.module.test.dto.TestEnumRequest;
import com.lv.fast.module.test.dto.TestRequest;
import com.lv.fast.module.test.enums.TestEnum;
import com.lv.fast.module.test.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author lvlaotou
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

    @GetMapping("/testHash")
    public RestResult testHash(@RequestParam long param){
        return RestResult.success(testService.testRedisCache(param));
    }

    @GetMapping("/testEvict")
    public RestResult testEvict(@RequestParam long param){
        testService.testRedisEvict(param);
        return RestResult.success();
    }

    @GetMapping("/testBatchEvict")
    public RestResult testBatchEvict(@RequestParam long param){
        testService.testRedisBatchEvict(param);
        return RestResult.success();
    }

    @GetMapping("/test/enum")
    public RestResult testEnumParam(@RequestParam TestEnum param){
        return RestResult.success(param);
    }

    @PostMapping("/enum")
    public RestResult testEnum(@RequestBody TestEnumRequest param){
        return RestResult.success(param);
    }
}

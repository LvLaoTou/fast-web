package com.lv.fast.model.test.controller;

import com.lv.fast.common.entity.RestResult;
import com.lv.fast.model.test.dto.TestRequest;
import com.lv.fast.model.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jie.lv
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/get")
    public RestResult test(@Validated TestRequest request){
        return RestResult.success(testService.convert(request));
    }
}

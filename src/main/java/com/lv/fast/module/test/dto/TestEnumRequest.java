package com.lv.fast.module.test.dto;

import com.lv.fast.module.test.enums.TestEnum;
import lombok.Data;

/**
 * 测试枚举请求参数对象
 * @author lvlaotou
 */
@Data
public class TestEnumRequest {

    private TestEnum testEnum;
}

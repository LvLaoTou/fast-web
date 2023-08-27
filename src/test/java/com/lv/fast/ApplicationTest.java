package com.lv.fast;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
public class ApplicationTest {

    @Test
    void test(){
        System.out.println(new String("我们用Java实现一个多次执行结果永不重复的字符串，要求字符串长度8到20个字符，由数字和大小写字母组成".getBytes(), StandardCharsets.UTF_8).codePointCount(0, 2));
    }
}

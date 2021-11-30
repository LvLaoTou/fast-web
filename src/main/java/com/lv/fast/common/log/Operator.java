package com.lv.fast.common.log;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日志操作者
 * @author jie.lv
 */
@Data
@Builder
@NoArgsConstructor
public class Operator {

    /** id */
    private String id;

    /** 用户名 */
    private String name;
}

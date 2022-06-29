package com.lv.fast.common.valid;

/**
 * 全局统一描述接口
 * @author lvlaotou
 */
public interface Describe<T> extends Code<T> {

    /**
     * 获取描述
     * @return 描述
     */
    String getDescribe();
}

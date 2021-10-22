package com.lv.fast.common.valid;

/**
 *  全局带标识码的接口
 * @author jie.lv
 */
public interface Code<T> {

    /**
     * 获取标识码
     * @return 标识码
     */
    T getCode();
}

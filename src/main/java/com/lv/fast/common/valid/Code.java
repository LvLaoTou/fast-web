package com.lv.fast.common.valid;

/**
 * @Description 全局带标识码的接口
 * @Author jie.lv
 */
interface Code<T> {

    /**
     * 获取标识码
     * @return 标识码
     */
    T getCode();
}

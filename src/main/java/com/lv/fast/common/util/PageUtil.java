package com.lv.fast.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分页工具类
 * @author lvlaotou
 */
public class PageUtil {

    private PageUtil(){}

    /**
     * mybatis plus 分页对象类型转换
     * @param source 源对象
     * @param target 目标对象
     * @param <T> 分页对象枚举对象
     * @param <S> 目标对象
     * @return 新的分页对象
     */
    @SuppressWarnings("unused")
    public static  <T,S> IPage<T> pageBeanConvert(IPage<S> source, Class<T> target){
        List<S> records = source.getRecords();
        IPage<T> targetPage = new Page<>();
        BeanUtil.copyProperties(source,targetPage);
        if (CollectionUtil.isEmpty(records)){
            List<T> targetList = new ArrayList<>();
            targetPage.setRecords(targetList);
            return targetPage;
        }
        List<T> collect = source.getRecords().stream()
                .map(s -> BeanUtil.copyProperties(s, target))
                .collect(Collectors.toList());
        targetPage.setRecords(collect);
        return targetPage;
    }
}

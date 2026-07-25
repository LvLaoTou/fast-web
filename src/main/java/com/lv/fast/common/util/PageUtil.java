package com.lv.fast.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lv.fast.common.entity.PageQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分页工具类
 *
 * @author lvlaotou
 * @since 2026-07-25
 * @generated-by oh-my-pi (qwen3.8-max-preview)
 */
public class PageUtil {

    private PageUtil(){}

    /**
     * mybatis plus 分页对象类型转换
     * @param source 源分页对象
     * @param target 目标记录类型
     * @param <S> 源记录类型
     * @param <T> 目标记录类型
     * @return 新的分页对象
     */
    public static <S, T> IPage<T> pageBeanConvert(IPage<S> source, Class<T> target){
        Assert.notNull(source, "源分页对象不能为空");
        List<S> records = source.getRecords();
        IPage<T> targetPage = new Page<>();
        BeanUtil.copyProperties(source, targetPage);
        if (CollectionUtil.isEmpty(records)){
            targetPage.setRecords(new ArrayList<>());
            return targetPage;
        }
        List<T> collect = records.stream()
                .map(s -> BeanUtil.copyProperties(s, target))
                .collect(Collectors.toList());
        targetPage.setRecords(collect);
        return targetPage;
    }

    /**
     * 获取mybatis分页对象
     * @param pageQuery 分页查询参数
     * @return 分页对象
     */
    public static Page<?> getPage(PageQuery pageQuery){
        Assert.notNull(pageQuery, "分页查询参数不能为空");
        return new Page<>(pageQuery.getPageIndex(), pageQuery.getPageSize());
    }
}

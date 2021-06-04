package com.lv.fast.util;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lv.fast.DTO.PageQuery;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分页工具类
 * @author jie.lv
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
    public static  <T,S> IPage<T> pageBeanConvert(IPage<S> source, Class<T> target){
        List<T> collect = source.getRecords().stream()
                .map(s -> BeanUtil.copyProperties(s, target))
                .collect(Collectors.toList());
        IPage<T> targetPage = new Page<>();
        BeanUtil.copyProperties(source,targetPage);
        targetPage.setRecords(collect);
        return targetPage;
    }

    /**
     * 获取mybatis分页对象
     * @param pageQuery 分页对象
     * @param <T> 泛型
     * @return 分页对象
     */
    public static <T extends PageQuery> Page getPage(T pageQuery){
        return new Page<>(pageQuery.getPageIndex(),pageQuery.getPageSize());
    }
}

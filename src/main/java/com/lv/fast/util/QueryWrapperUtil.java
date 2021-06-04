package com.lv.fast.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lv.fast.DTO.PageQuery;
import com.lv.fast.enums.OrderTypeEnum;
import com.lv.fast.constant.RestResultCodeConstant;
import com.lv.fast.exception.MyException;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 查询条件工具类
 * @author lv
 * @version 1.0.0
 */
public class QueryWrapperUtil {

    private QueryWrapperUtil(){}

    /**
     * 获取分页查询条件
     * @param pageQuery 分页对象
     * @param columns 关键字需要匹配的列
     * @param <Q> 泛型
     * @param <T> 泛型
     * @return 查询条件对象
     */
    @SneakyThrows
    public static <Q extends PageQuery,T> QueryWrapper<T> getPageQueryWrapper(Q pageQuery, String... columns){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        String key = pageQuery.getKey();
        if (StringUtils.isNotBlank(key)){
            for (int i = 0; i < columns.length; i++) {
                queryWrapper.like(columns[i],key);
                if (i < columns.length - 1){
                    queryWrapper.or();
                }
            }
        }
        if (Objects.equals(pageQuery.getOrder(), OrderTypeEnum.ASC.getCode())){
            queryWrapper.orderByAsc(pageQuery.getSortBy());
            return queryWrapper;
        }
        if (Objects.equals(pageQuery.getOrder(), OrderTypeEnum.DESC.getCode())){
            queryWrapper.orderByDesc(pageQuery.getSortBy());
            return queryWrapper;
        }
        throw new MyException(RestResultCodeConstant.PARAM_ERROR, "分页搜索，排序类型非法");
    }

    /**
     * 获取分页查询对象
     * @param orderTypeEnum 排序类型
     * @param orderFields 排序字段
     * @return 查询条件对象
     */
    public static QueryWrapper getOrderByQueryWrapper(OrderTypeEnum orderTypeEnum, Object[] orderFields){
        QueryWrapper queryWrapper = new QueryWrapper();
        if (orderTypeEnum == OrderTypeEnum.DESC){
            queryWrapper.orderByDesc(orderFields);
            return queryWrapper;
        }
        if (orderTypeEnum == OrderTypeEnum.ASC){
            queryWrapper.orderByAsc(orderFields);
            return queryWrapper;
        }
        throw new MyException(RestResultCodeConstant.PARAM_ERROR, "分页搜索，排序类型非法");
    }

    /**
     * 获取倒序查询条件
     * @param orderFields 排序字段
     * @return 查询条件对象
     */
    public static QueryWrapper getOrderByDescQueryWrapper(String... orderFields){
        return getOrderByQueryWrapper(OrderTypeEnum.DESC, orderFields);
    }

    /**
     * 获取升序插叙条件
     * @param orderFields 排序字段
     * @return 查询条件对象
     */
    public static QueryWrapper getOrderByAscQueryWrapper(String... orderFields){
        return getOrderByQueryWrapper(OrderTypeEnum.ASC, orderFields);
    }

    /**
     * 获取倒序查询条件
     * @return 查询条件对象
     */
    public static QueryWrapper getOrderByDescQueryWrapper(){
        return getOrderByQueryWrapper(OrderTypeEnum.DESC, new String[]{"create_time"});
    }

    /**
     * 获取升序查询条件
     * @return 查询条件对象
     */
    public static QueryWrapper getOrderByAscQueryWrapper(){
        return getOrderByQueryWrapper(OrderTypeEnum.ASC, new String[]{"create_time"});
    }
}

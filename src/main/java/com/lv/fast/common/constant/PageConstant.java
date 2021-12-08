package com.lv.fast.common.constant;


/**
 * 分页常量类
 * @author jie.lv
 */
public class PageConstant {

    private PageConstant(){}

    /**
     * 默认分页当前页码
     */
    public static final int DEFAULT_PAGE_INDEX = 1;

    /**
     * 最小页码
     */
    public static final int MIN_PAGE_INDEX = 1;

    /**
     * 最小分页每页显示数量
     */
    public static final int MIN_PAGE_SIZE = 1;

    /**
     * 默认分页每页显示数量
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大分页每页显示数量
     */
    public static final int MAX_PAGE_SIZE = 100;

    /**
     * 默认分页排序方式
     */
    public static final String DEFAULT_PAGE_ORDER_TYPE = "desc";

    /**
     * 默认分页排序字段
     */
    public static final String DEFAULT_PAGE_ORDER_FIELD = "created_time";

    /**
     * 数据库单词分隔符
     */
    public static final String SEPARATOR_BEAN_NAME = "_";

    /**
     * 搜索关键字最大长度
     */
    public static final int SEARCH_KEY_MAX_LENGTH = 20;
}

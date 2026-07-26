package com.lv.fast.common.enums;

import com.lv.fast.common.entity.Describe;

/**
 * 全局统一响应枚举
 *
 * <p>状态码设计规约 — 组成规则：错误来源标识 + 模块 + 序号</p>
 * <ul>
 *   <li>错误来源标识：1 → 用户操作错误，2 → 服务器错误，3 → 外部系统错误</li>
 *   <li>模块：两位数 01-99，00 保留</li>
 *   <li>序号：两位数 01-99，00 保留</li>
 *   <li>成功：0</li>
 * </ul>
 *
 * <p>describe 为默认兜底文案，业务代码可通过
 * {@code new BusinessException(RestResultEnum.PARAM_ERROR, "用户名错误")} 覆盖具体描述。
 * 业务模块可定义自己的枚举实现 {@link Describe} 接口扩展错误码。</p>
 *
 * @author lvlaotou
 */
public enum RestResultEnum implements Describe<String> {

    /** 成功 */
    SUCCESS("0", "success"),

    /** 服务端未知错误 */
    UNKNOWN_ERROR("20000", "未知异常"),

    /** 参数错误 */
    PARAM_ERROR("10101", "参数错误"),

    /** 数据库记录已存在 */
    DATABASE_EXIST_ERROR("10201", "数据库中该记录已存在"),

    /** 数据库记录不存在 */
    DATABASE_NOT_EXIST_ERROR("10202", "数据库中该记录不存在"),

    /** 请求路径不存在 */
    PATH_NOT_FOUND("10301", "请求地址不存在");

    /** 标识码 */
    private final String code;

    /** 默认描述（可被 BusinessException 覆盖） */
    private final String describe;

    RestResultEnum(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescribe() {
        return this.describe;
    }
}

package com.lv.fast.exception;

import com.lv.fast.common.entity.Describe;
import com.lv.fast.common.enums.RestResultEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义业务异常
 *
 * <p>推荐使用 {@link Describe} 枚举构造，describe 为默认兜底文案，
 * 可通过 {@link #BusinessException(Describe, String)} 覆盖具体描述：</p>
 * <pre>{@code
 * // 使用枚举默认描述
 * throw new BusinessException(RestResultEnum.DATABASE_NOT_EXIST_ERROR);
 *
 * // 同一个 code，不同业务描述
 * throw new BusinessException(RestResultEnum.PARAM_ERROR, "用户名错误");
 * throw new BusinessException(RestResultEnum.PARAM_ERROR, "密码错误");
 * }</pre>
 *
 * @author lvlaotou
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "发生自定义异常")
public class BusinessException extends RuntimeException implements Describe<String> {

    /** 状态码 */
    private final String code;

    /** 描述信息 */
    private final String describe;

    /**
     * 无参构造，默认未知错误
     */
    @SuppressWarnings("unused")
    public BusinessException() {
        this(RestResultEnum.UNKNOWN_ERROR);
    }

    /**
     * 使用错误码枚举构造，描述取枚举默认值
     * @param describe 错误码枚举
     */
    public BusinessException(Describe<String> describe) {
        super(describe.getDescribe());
        this.code = describe.getCode();
        this.describe = describe.getDescribe();
    }

    /**
     * 使用错误码枚举构造，覆盖描述信息
     *
     * <p>同一个 code 可对应不同的业务描述，例如：</p>
     * <pre>{@code
     * throw new BusinessException(RestResultEnum.PARAM_ERROR, "用户名错误");
     * throw new BusinessException(RestResultEnum.PARAM_ERROR, "密码错误");
     * }</pre>
     *
     * @param describe 错误码枚举（提供 code）
     * @param message  覆盖的描述信息
     */
    public BusinessException(Describe<String> describe, String message) {
        super(message);
        this.code = describe.getCode();
        this.describe = message;
    }

    /**
     * 完全自定义 code 和描述（仅用于无法归入枚举的特殊场景）
     */
    public BusinessException(String code, String describe) {
        super(describe);
        this.code = code;
        this.describe = describe;
    }

    /**
     * 仅传描述，code 默认 PARAM_ERROR
     */
    public BusinessException(String describe) {
        this(RestResultEnum.PARAM_ERROR, describe);
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescribe() {
        return describe;
    }
}

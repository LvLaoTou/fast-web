package com.lv.fast.common.entity;

import com.lv.fast.common.constant.DataBaseConstant;
import com.lv.fast.common.constant.PageConstant;
import com.lv.fast.common.enums.OrderTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * 分页查询对象
 * @author lv
 */
@Data
@Validated
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PageQuery {

    @Schema(description = "当前页码",
            example = "1",
            minimum = ""+PageConstant.MIN_PAGE_INDEX,
            defaultValue = ""+PageConstant.DEFAULT_PAGE_INDEX)
    @Min(value = PageConstant.MIN_PAGE_INDEX, message = "分页参数:当前页码不能小于"+PageConstant.MIN_PAGE_INDEX)
    private Integer pageIndex = PageConstant.DEFAULT_PAGE_INDEX;

    @Schema(description = "每页显示数量",
            example = "10",
            maximum = ""+PageConstant.MAX_PAGE_SIZE ,
            minimum = ""+PageConstant.MIN_PAGE_SIZE,
            defaultValue = ""+PageConstant.DEFAULT_PAGE_SIZE)
    @Min(value = PageConstant.MIN_PAGE_SIZE, message = "分页参数:每页显示数量最小为"+PageConstant.MIN_PAGE_SIZE)
    @Max(value = PageConstant.MAX_PAGE_SIZE, message = "分页参数:每页显示数量最大为"+PageConstant.MAX_PAGE_SIZE)
    private Integer pageSize = PageConstant.DEFAULT_PAGE_SIZE;

    @Schema(description = "搜索关键字", example = "deleteToken", maxLength = PageConstant.SEARCH_KEY_MAX_LENGTH)
    @Length(max = PageConstant.SEARCH_KEY_MAX_LENGTH, message = "搜索关键字最多"+PageConstant.SEARCH_KEY_MAX_LENGTH+"个字符")
    private String key;

    @Schema(description = "排序方式", example = "desc", defaultValue = PageConstant.DEFAULT_PAGE_ORDER_TYPE)
    private OrderTypeEnum order = OrderTypeEnum.DESC;

    @Schema(description = "排序字段", example = "created_time", maxLength = DataBaseConstant.FIELD_MAX_LENGTH, defaultValue = PageConstant.DEFAULT_PAGE_ORDER_FIELD)
    @Pattern(regexp = "[A-Za-z0-9_]{1,64}", message = "排序字段格式非法")
    @Length(max = DataBaseConstant.FIELD_MAX_LENGTH, message = "排序字段最多"+DataBaseConstant.FIELD_MAX_LENGTH+"个字符")
    private String sortBy = PageConstant.DEFAULT_PAGE_ORDER_FIELD;
}

package com.lv.fast.DTO;

import com.lv.fast.annotation.EnumCheck;
import com.lv.fast.constant.PageConstant;
import com.lv.fast.enums.OrderTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * 分页查询对象
 * @author lv
 * @version 1.0.0
 */
@Data
@Validated
public class PageQuery {

    @ApiModelProperty(value = "当前页码,默认为"+PageConstant.DEFAULT_PAGE_INDEX+",最小为"+PageConstant.MIN_PAGE_INDEX, example = "1")
    @Min(value = PageConstant.MIN_PAGE_INDEX, message = "分页参数:当前页码不能小于"+PageConstant.MIN_PAGE_INDEX)
    private Integer pageIndex = PageConstant.DEFAULT_PAGE_INDEX;

    @ApiModelProperty(value = "每页显示数量,默认为"+PageConstant.DEFAULT_PAGE_SIZE+",最小为"+PageConstant.MIN_PAGE_SIZE+",最大为"+PageConstant.MAX_PAGE_SIZE,
            example = "10")
    @Min(value = PageConstant.MIN_PAGE_SIZE, message = "分页参数:每页显示数量最小为"+PageConstant.MIN_PAGE_SIZE)
    @Max(value = PageConstant.MAX_PAGE_SIZE, message = "分页参数:每页显示数量最大为"+PageConstant.MAX_PAGE_SIZE)
    private Integer pageSize = PageConstant.DEFAULT_PAGE_SIZE;

    @ApiModelProperty(value = "搜索关键字", example = "deleteToken")
    private String key;

    @ApiModelProperty(value = "排序方式,默认为倒序desc", example = "desc")
    @EnumCheck(enumClass = OrderTypeEnum.class)
    private String order = PageConstant.DEFAULT_PAGE_ORDER_TYPE;

    @ApiModelProperty(value = "排序字段,默认为创建时间", example = "created_time")
    @Pattern(regexp = "[A-Za-z0-9_]+", message = "排序字段格式非法")
    private String sortBy = PageConstant.DEFAULT_PAGE_ORDER_FIELD;
}

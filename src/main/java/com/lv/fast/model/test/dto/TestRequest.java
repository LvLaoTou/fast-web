package com.lv.fast.model.test.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jie.lv
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("测试请求参数对象")
public class TestRequest {

    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", example = "1", required = true)
    private Long id;

    @NotBlank(message = "名称不能为空")
    @Length(max = 50, message = "名称最大长度为50个字符")
    @ApiModelProperty(value = "姓名 最大长度50字符", example = "张三", required = true)
    private String name;

    @ApiModelProperty(value = "是否成功", example = "true", required = true)
    private Boolean success;
}

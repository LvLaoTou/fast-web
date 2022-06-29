package com.lv.fast.module.test.dto;

import com.lv.fast.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author lvlaotou
 */
@Data
@Validated
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "测试请求参数对象")
public class TestRequest extends PageQuery {

    @NotNull(message = "id不能为空")
    @Schema(description = "id", example = "1", required = true)
    private Long id;

    @NotBlank(message = "名称不能为空")
    @Length(max = 50, message = "名称最大长度为50个字符")
    @Schema(description = "姓名", example = "张三", required = true, maxLength = 50)
    private String name;

    @Schema(description = "是否成功", example = "true")
    private Boolean success;
}

package com.lv.fast.module.test.dto;

import com.lv.fast.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;


/**
 * @author lvlaotou
 */
@Data
@Validated
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "测试请求参数对象")
public class TestRequest extends PageQuery {

    @NotNull(message = "id不能为空")
    @Schema(description = "id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @NotBlank(message = "名称不能为空")
    @Length(max = 50, message = "名称最大长度为50个字符")
    @Schema(description = "姓名", example = "张三", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 50)
    private String name;

    @Schema(description = "是否成功", example = "true")
    private Boolean success;
}

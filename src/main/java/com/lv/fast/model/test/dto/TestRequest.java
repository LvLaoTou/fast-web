package com.lv.fast.model.test.dto;

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
public class TestRequest {

    @NotNull(message = "id不能为空")
    private Long id;

    @NotBlank(message = "名称不能为空")
    @Length(max = 50, message = "名称最大长度为50个字符")
    private String name;

    private Boolean success;
}

package com.lv.fast.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lv.fast.common.entity.EnumInterface;
import com.lv.fast.common.util.EnumUtil;

/**
 * Jackson 2 枚举反序列化模块
 *
 * <p>供 {@link com.lv.fast.common.constant.JsonConstant} 中的 Jackson 2 ObjectMapper 使用，
 * 功能与 {@link EnumInterfaceModule}（Jackson 3）一致：
 * 自动为所有实现 {@link EnumInterface} 的枚举注册基于 code 的反序列化。</p>
 *
 * @author lvlaotou
 * @since 2026-07-25
 * @generated-by oh-my-pi (qwen3.8-max-preview)
 */
public class EnumInterfaceJackson2Module extends SimpleModule {

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.addDeserializers(new Deserializers.Base() {
            @Override
            public JsonDeserializer<?> findEnumDeserializer(Class<?> type,
                    DeserializationConfig config, BeanDescription beanDesc) {
                if (EnumInterface.class.isAssignableFrom(type)) {
                    return new EnumCodeDeserializer(type);
                }
                return null;
            }
        });
    }

    /**
     * 基于 code 匹配的枚举反序列化器
     *
     * <p>读取 JSON 原始值的文本形式，委托 {@link EnumUtil#getEnumByCode} 按 code 匹配枚举项。</p>
     */
    private static class EnumCodeDeserializer extends JsonDeserializer<Enum<?>> {

        private final Class<?> enumClass;

        EnumCodeDeserializer(Class<?> enumClass) {
            this.enumClass = enumClass;
        }

        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt) throws java.io.IOException {
            return EnumUtil.getEnumByCode((Class) enumClass, p.getText());
        }
    }
}

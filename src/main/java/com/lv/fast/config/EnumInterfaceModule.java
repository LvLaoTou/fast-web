package com.lv.fast.config;

import com.lv.fast.common.entity.EnumInterface;
import com.lv.fast.common.util.EnumUtil;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.DeserializationConfig;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.Deserializers;
import tools.jackson.databind.module.SimpleModule;

/**
 * Jackson 3 枚举反序列化模块
 *
 * <p>自动为所有实现 {@link EnumInterface} 的枚举注册基于 code 的反序列化，
 * 枚举无需再手写 {@code @JsonCreator} + {@code match} 样板方法。</p>
 *
 * @author lvlaotou
 * @since 2026-07-25
 * @generated-by oh-my-pi (qwen3.8-max-preview)
 */
public class EnumInterfaceModule extends SimpleModule {

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.addDeserializers(new Deserializers.Base() {
            @Override
            public boolean hasDeserializerFor(DeserializationConfig config, Class<?> type) {
                return EnumInterface.class.isAssignableFrom(type);
            }

            @Override
            public ValueDeserializer<?> findEnumDeserializer(JavaType type,
                    DeserializationConfig config, BeanDescription.Supplier beanDescRef) {
                if (EnumInterface.class.isAssignableFrom(type.getRawClass())) {
                    return new EnumCodeDeserializer(type.getRawClass());
                }
                return null;
            }
        });
    }

    /**
     * 基于 code 匹配的枚举反序列化器
     *
     * <p>读取 JSON 原始值的文本形式，委托 {@link EnumUtil#getEnumByCode} 按 code 匹配枚举项。
     * {@code EnumUtil} 内部通过 {@code toString} 比较，因此 String / Number 类型的 code 均可匹配。</p>
     */
    private static class EnumCodeDeserializer extends ValueDeserializer<Enum<?>> {

        private final Class<?> enumClass;

        EnumCodeDeserializer(Class<?> enumClass) {
            this.enumClass = enumClass;
        }

        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt) {
            return EnumUtil.getEnumByCode((Class) enumClass, p.getText());
        }
    }
}

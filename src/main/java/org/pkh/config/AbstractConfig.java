package org.pkh.config;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.pkh.Required;

import java.lang.reflect.Field;

@Slf4j
public abstract class AbstractConfig {
    /**
     * 检查
     *
     * @return boolean
     */
    @SneakyThrows
    protected boolean check() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Required annotation = field.getAnnotation(Required.class);
            if (annotation != null) {
                field.setAccessible(true);
                Object value = field.get(this);
                if (value == null) {
                    throw new RuntimeException(StrUtil.format("{}-{}", field.getName(), annotation.message()));
                }
            }
        }
        return true;
    }
}

package org.pkh.database;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldType {
    /**
     * 包裹
     */
    private String pkg;
    /**
     * 类型
     */
    private String type;
    /**
     * ts型
     */
    private String tsType;

    /**
     * 是否完整
     *
     * @return boolean
     */
    public boolean isComplete() {
        return StrUtil.isNotBlank(pkg) && StrUtil.isNotBlank(type);
    }
}

package org.pkh.database;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldType {
    /**
     * 包裹
     */
    private String pkg;
    /**
     * 类型
     */
    private String type;
}

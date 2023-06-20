package org.pkh.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldType {
    /**
     * 包
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
}

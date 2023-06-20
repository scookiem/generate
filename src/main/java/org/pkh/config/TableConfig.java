package org.pkh.config;

import lombok.Data;
import org.pkh.database.EFieldType;
import org.pkh.database.FieldType;

import java.util.List;
import java.util.Map;

/**
 * 表配置
 *
 * @author Administrator
 * @date 2021/06/09
 */
@Data
public class TableConfig extends AbstractConfig {
    /**
     * 包括前缀
     */
    private String includePrefix;
    /**
     * 包括列表
     */
    private List<String> includeTableList;
    /**
     * 排除前缀
     */
    private String excludePrefix;
    /**
     * 排除列表
     */
    private List<String> excludeTableList;
    /**
     * 排除列列表
     */
    private List<String> excludeColumnList;

    /**
     * 前缀
     */
    private String prefix;
    /**
     * 按类型转换
     */
    private Map<EFieldType, EFieldType> turnType;
    /**
     * 按字段转换
     */
    private Map<String, FieldType> turnField;
}

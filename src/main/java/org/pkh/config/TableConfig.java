package org.pkh.config;

import lombok.Data;
import org.pkh.database.EFieldType;

import java.util.List;
import java.util.Map;

/**
 * 表配置
 *
 * @author Administrator
 * @date 2021/06/09
 */
@Data
public class TableConfig {
    /**
     * 包括列表
     */
    private List<String> includeTableList;
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
     * 把类型
     */
    private Map<EFieldType, EFieldType> turnType;
    /**
     * 将字段
     */
    private Map<String, EFieldType> turnField;

}

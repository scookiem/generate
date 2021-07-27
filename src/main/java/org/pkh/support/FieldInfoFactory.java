package org.pkh.support;

import cn.hutool.core.collection.CollectionUtil;
import lombok.SneakyThrows;
import org.pkh.config.ConfigHolder;
import org.pkh.database.FieldType;
import org.pkh.info.FieldInfo;

import java.sql.ResultSet;

/**
 * 字段信息工厂
 *
 * @author Administrator
 * @date 2021/07/26
 */
public class FieldInfoFactory {
    private Process process;

    public FieldInfoFactory() {
        /*有排除名单*/
        if (CollectionUtil.isNotEmpty(ConfigHolder.TABLE_CONFIG.getExcludeColumnList())) {
            process = new Process() {
                @Override
                FieldInfo process() {
                    if (ConfigHolder.TABLE_CONFIG.getExcludeColumnList().contains(columnName)) {
                        return null;
                    }
                    return new FieldInfo(columnName, columnType, fieldType, comment);
                }
            };
        } else {
            process = new Process() {
                @Override
                FieldInfo process() {
                    return new FieldInfo(columnName, columnType, fieldType, comment);
                }
            };
        }
    }

    /**
     * 获取字段信息
     *
     * @param columns 列
     * @return {@link FieldInfo}
     */
    public FieldInfo getFieldInfo(ResultSet columns) {
        return process.getFieldInfo(columns);
    }

    /**
     * 植被类型
     *
     * @param originType 来源类型
     * @return {@link FieldType}
     */
    private FieldType coverType(String originType) {
        FieldType fieldType = null;
        boolean skip = false;
        if (containsAny(originType, "char", "text", "json", "enum")) {
            fieldType = FieldType.STRING;
            skip = true;
        }
        if (!skip && containsAny(originType, "bigint")) {
            fieldType = FieldType.LONG;
            skip = true;
        }
        if (!skip && containsAny(originType, "int")) {
            fieldType = FieldType.INTEGER;
            skip = true;
        }
        if (!skip && containsAny(originType, "date", "time")) {
            fieldType = FieldType.DATE;
            skip = true;
        }
        if (!skip && containsAny(originType, "bit", "boolean")) {
            fieldType = FieldType.BOOLEAN;
            skip = true;
        }
        if (!skip && containsAny(originType, "decimal", "numeric")) {
            fieldType = FieldType.BIG_DECIMAL;
            skip = true;
        }
        if (!skip && containsAny(originType, "bytea")) {
            fieldType = FieldType.BYTE_ARRAY;
            skip = true;
        }
        if (!skip && containsAny(originType, "float")) {
            fieldType = FieldType.FLOAT;
            skip = true;
        }
        if (!skip && containsAny(originType, "double")) {
            fieldType = FieldType.DOUBLE;
        }
        if (fieldType == null) {
            throw new RuntimeException("未知类型:" + originType);
        }
        if (CollectionUtil.isNotEmpty(ConfigHolder.TABLE_CONFIG.getTurnType())) {
            FieldType turnType = ConfigHolder.TABLE_CONFIG.getTurnType().get(fieldType);
            if (turnType != null) {
                fieldType = turnType;
            }
        }
        return fieldType;
    }

    /**
     * 包含任何
     *
     * @param text 文本
     * @return boolean
     */
    private boolean containsAny(String originType, String... text) {
        for (String s : text) {
            if (originType.contains(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 过程
     *
     * @author Administrator
     * @date 2021/07/26
     */
    private abstract class Process {
        protected String columnName;
        protected String columnType;
        protected String comment;
        protected FieldType fieldType;

        @SneakyThrows
        public FieldInfo getFieldInfo(ResultSet rs) {
            this.columnName = rs.getString("COLUMN_NAME");
            this.columnType = rs.getString("TYPE_NAME");
            this.comment = rs.getString("REMARKS");
            this.fieldType = null;
            if (CollectionUtil.isNotEmpty(ConfigHolder.TABLE_CONFIG.getTurnField())) {
                FieldType turnType = ConfigHolder.TABLE_CONFIG.getTurnField().get(columnName);
                if (turnType != null) {
                    fieldType = turnType;
                }
            }
            if (fieldType == null) {
                fieldType = coverType(columnType);
            }
            return process();
        }

        abstract FieldInfo process();
    }
}
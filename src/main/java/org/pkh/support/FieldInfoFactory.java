package org.pkh.support;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.pkh.config.ConfigHolder;
import org.pkh.database.EFieldType;
import org.pkh.database.FieldType;
import org.pkh.info.FieldInfo;

import java.sql.ResultSet;

/**
 * 字段信息工厂
 *
 * @author Administrator
 * @date 2021/07/26
 */
@Slf4j
public abstract class FieldInfoFactory {
    @SneakyThrows
    public static FieldInfo getFieldInfo(ResultSet rs) {
        FieldInfo result = new FieldInfo();
        result.setOriginName(rs.getString("COLUMN_NAME"));
        result.setOriginType(rs.getString("TYPE_NAME"));
        result.setComment(rs.getString("REMARKS"));
        result.setNullable(rs.getInt("NULLABLE"));
        if (ConfigHolder.TABLE_CONFIG.getExcludeColumnList().contains(result.getOriginName())) {
            return null;
        }
        /*pkg优先级大于type*/
        if (CollectionUtil.isNotEmpty(ConfigHolder.TABLE_CONFIG.getTurnField())) {
            FieldType fieldType = ConfigHolder.TABLE_CONFIG.getTurnField().get(result.getOriginName());
            if (fieldType != null) {
                /*有字段配置 无type无pkg*/
                if (StrUtil.isBlank(fieldType.getType()) && StrUtil.isBlank(fieldType.getPkg())) {
                    throw new RuntimeException(StrUtil.format("类型或包不能全为空"));
                }
                /*pkg*/
                else if (StrUtil.isNotBlank(fieldType.getPkg())) {
                    fieldType.setType(StrUtil.subAfter(fieldType.getPkg(), ".", true));
                    fieldType.setTsType(fieldType.getTsType());
                    result.setType(fieldType);
                }
                /*type*/
                else {
                    try {
                        EFieldType eFieldType = EFieldType.valueOf(fieldType.getType().toLowerCase());
                        result.setType(eFieldType.getType());
                    } catch (RuntimeException e) {
                        throw new RuntimeException(StrUtil.format("{}未找到预设类型,请补充完善", fieldType.getType()));
                    }
                }
                if (StrUtil.isBlank(result.getType().getTsType())) {
                    result.getType().setTsType("string");
                }
            }
        }
        if (result.getType() == null) {
            result.setType(coverType(result.getOriginType().toLowerCase()).getType());
        }
        return result;
    }


    /**
     * 包含任何
     *
     * @param text 文本
     * @return boolean
     */
    private static boolean containsAny(String originType, String... text) {
        for (String s : text) {
            if (originType.contains(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 植被类型
     *
     * @param originType 来源类型
     * @return {@link EFieldType}
     */
    private static EFieldType coverType(String originType) {
        EFieldType fieldType = null;
        boolean skip = false;
        if (containsAny(originType, "char", "text", "json", "enum", "varchar")) {
            fieldType = EFieldType.STRING;
            skip = true;
        }
        if (!skip && containsAny(originType, "bigint")) {
            fieldType = EFieldType.LONG;
            skip = true;
        }
        if (!skip && containsAny(originType, "int")) {
            fieldType = EFieldType.INTEGER;
            skip = true;
        }
        if (!skip && containsAny(originType, "date", "time")) {
            fieldType = EFieldType.DATE;
            skip = true;
        }
        if (!skip && containsAny(originType, "bit", "boolean", "bool", "tinyint")) {
            fieldType = EFieldType.BOOLEAN;
            skip = true;
        }
        if (!skip && containsAny(originType, "decimal", "numeric")) {
            fieldType = EFieldType.BIG_DECIMAL;
            skip = true;
        }
        if (!skip && containsAny(originType, "bytea")) {
            fieldType = EFieldType.BYTE_ARRAY;
            skip = true;
        }
        if (!skip && containsAny(originType, "float")) {
            fieldType = EFieldType.FLOAT;
            skip = true;
        }
        if (!skip && containsAny(originType, "double")) {
            fieldType = EFieldType.DOUBLE;
        }
        if (fieldType == null) {
            throw new RuntimeException("未知类型:" + originType);
        }
        if (CollectionUtil.isNotEmpty(ConfigHolder.TABLE_CONFIG.getTurnType())) {
            EFieldType turnType = ConfigHolder.TABLE_CONFIG.getTurnType().get(fieldType);
            if (turnType != null) {
                fieldType = turnType;
            }
        }
        return fieldType;
    }
}

package org.pkh.info;

import com.google.common.base.CaseFormat;
import lombok.Data;
import org.pkh.database.FieldType;

import java.util.List;

/**
 * 字段信息
 *
 * @author Administrator
 * @date 2021/06/09
 */
@Data
public class FieldInfo {
    /**
     * 名字
     */
    private String name;
    /**
     * 大驼峰
     */
    private String upperCamel;
    /**
     * 小驼峰
     */
    private String lowerCamel;
    /**
     * 下划线
     */
    private String underscore;
    /**
     * 来源类型
     */
    private String originType;
    /**
     * 类型
     */
    private FieldType type;
    /**
     * 备注
     */
    private String comment;
    /**
     * 注解列表
     */
    private List<String> annotationList;

    public FieldInfo(String name, String originType, String comment) {
        this.name = name;
        this.upperCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name);
        this.lowerCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.name);
        this.underscore = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, this.name);
        this.originType = originType;
        this.comment = comment;
        this.type = coverType();
    }

    private FieldType coverType() {
        if (containsAny("char", "text", "json", "enum")) {
            return FieldType.STRING;
        }
        if (containsAny("bigint")) {
            return FieldType.LONG;
        }
        if (containsAny("int")) {
            return FieldType.INTEGER;
        }
        if (containsAny("date", "time")) {
            return FieldType.DATE;
        }
        if (containsAny("bit","boolean")) {
            return FieldType.BOOLEAN;
        }
        if (containsAny("decimal", "numeric")) {
            return FieldType.BIG_DECIMAL;
        }
        if (containsAny("bytea")) {
            return FieldType.BYTE_ARRAY;
        }
        if (containsAny("float")) {
            return FieldType.FLOAT;
        }
        if(containsAny("double")){
            return FieldType.DOUBLE;
        }
        throw new RuntimeException("未知类型");
    }

    private boolean containsAny(String... text) {
        for (String s : text) {
            if (this.originType.contains(s)) {
                return true;
            }
        }
        return false;
    }
}

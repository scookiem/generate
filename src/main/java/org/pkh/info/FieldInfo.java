package org.pkh.info;

import com.google.common.base.CaseFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pkh.database.FieldType;

import java.util.List;

/**
 * 字段信息
 *
 * @author Administrator
 * @date 2021/06/09
 */
@Data
@NoArgsConstructor
public class FieldInfo {
    /**
     * 名字
     */
    private String originName;
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

    /**
     * 0 (columnNoNulls) - 该列不允许为空
     * 1 (columnNullable) - 该列允许为空
     * 2 (columnNullableUnknown) - 不确定该列是否为空
     */
    private int nullable;

    public void setOriginName(String originName) {
        this.originName = originName;
        this.upperCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.originName);
        this.lowerCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.originName);
        this.underscore = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, this.originName);
    }
}

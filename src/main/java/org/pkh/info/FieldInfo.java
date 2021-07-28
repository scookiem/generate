package org.pkh.info;

import com.google.common.base.CaseFormat;
import lombok.Data;
import org.pkh.database.EFieldType;

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
    private EFieldType type;
    /**
     * 备注
     */
    private String comment;
    /**
     * 注解列表
     */
    private List<String> annotationList;

    /**
     * 字段信息
     *
     * @param name       的名字
     * @param originType 来源类型
     * @param fieldType  字段类型
     * @param comment    评论
     */
    public FieldInfo(String name, String originType, EFieldType fieldType, String comment) {
        this.name = name;
        this.upperCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name);
        this.lowerCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.name);
        this.underscore = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, this.name);
        this.originType = originType;
        this.type = fieldType;
        this.comment = comment;
    }
}

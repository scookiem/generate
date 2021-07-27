package org.pkh.info;

import com.google.common.base.CaseFormat;
import lombok.Data;
import org.pkh.config.ConfigHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 表信息
 *
 * @author Administrator
 * @date 2021/06/09
 */
@Data
public class TableInfo {
    /**
     * 包
     */
    private String packages;
    /**
     * 原产地名称
     */
    private String originName;
    /**
     * 表前缀
     */
    private String tablePrefix = ConfigHolder.TABLE_CONFIG.getPrefix();
    /**
     * 名字 去掉前缀的名字
     */
    private String name;
    /**
     * 注释
     */
    private String comment;
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
     * 导入列表
     */
    private Set<String> importSet = new HashSet<>();
    /**
     * 注解列表
     */
    private List<String> annotationList = new ArrayList<>();

    /**
     * 字段信息列表
     */
    private List<FieldInfo> fieldInfoList = new ArrayList<>();

    public TableInfo(String originName, String comment) {
        this.originName = originName;
        this.comment = comment;
        this.name = originName.replace(this.tablePrefix, "");
        this.upperCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name);
        this.lowerCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.name);
        this.underscore = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, this.name);
    }
}

package org.pkh.config;

import lombok.Data;
import org.pkh.Required;

/**
 * 全局配置
 *
 * @author Administrator
 * @date 2021/06/09
 */
@Data
public class GlobalConfig {
    /**
     * 根路径
     */
    @Required(message = "根路径不能为空")
    private String rootPath;
    /**
     * tpl路径
     */
    private String tplPath;
    /**
     * 作者
     */
    private String author;
    /**
     * 分隔符
     */
    private String delimiter = "/*--------分隔--------*/";
}

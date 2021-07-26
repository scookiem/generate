package org.pkh.config;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.pkh.Required;

import java.io.File;

/**
 * 文件配置
 *
 * @author Administrator
 * @date 2021/06/09
 */
@Data
public class FileConfig {
    /**
     * 文件名称
     */
    @Required(message = "文件名不能为空")
    private String fileName;
    /**
     * 更新文件的名字
     */
    private String updateFileName;
    /**
     * 输出路径
     */
    @Required(message = "输出路径不能为空", group = 1, least = 1)
    private String outputPath;
    /**
     * 输出包
     */
    @Required(message = "输出包不能为空", group = 1, least = 1)
    private String outputPackage;

    /**
     * 输出的名字
     */
    private String outputName;


    public void setFileName(String fileName) {
        if (!fileName.endsWith(".ftl")) {
            fileName = fileName + ".ftl";
        }
        this.fileName = fileName;
    }

    public void setUpdateFileName(String updateFileName) {
        if (!updateFileName.endsWith(".ftl")) {
            updateFileName = updateFileName + ".ftl";
        }
        this.updateFileName = updateFileName;
    }
    public String getOutputPackagePath() {
        return StrUtil.replace(this.outputPackage, ".", File.separator);
    }
}

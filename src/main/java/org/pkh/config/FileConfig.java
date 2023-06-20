package org.pkh.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.pkh.Required;

/**
 * 文件配置
 *
 * @author Administrator
 * @date 2021/06/09
 */
@Data
@Slf4j
public class FileConfig extends AbstractConfig {
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
     * 输出路径，相对路径以当前路径为开始
     */
    @Required(message = "输出路径不能为空")
    private String outputPath;
    /**
     * 输出的名字
     */
    @Required(message = "输出文件名不能为空")
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
}

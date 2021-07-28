package org.pkh.support;

import cn.hutool.core.util.StrUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.pkh.config.ConfigHolder;
import org.pkh.config.FileConfig;
import org.pkh.info.TableInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileGenerate implements Runnable {
    private static Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_31);
    private TableInfo tableInfo;

    static {
        Path path = Paths.get(ConfigHolder.GLOBAL_CONFIG.getTplPath());
        try {
            CONFIGURATION.setDirectoryForTemplateLoading(path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        CONFIGURATION.setDefaultEncoding("UTF-8");
        CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public FileGenerate(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @SneakyThrows
    @Override
    public void run() {
        for (FileConfig fileConfig : ConfigHolder.FILE_CONFIG) {
            Path path = Paths.get(ConfigHolder.GLOBAL_CONFIG.getRootPath(), fileConfig.getOutputPackagePath(), coverFileName(fileConfig.getOutputName()));
            tableInfo.setPackages(fileConfig.getOutputPackage());
            /*更新*/
            if (Files.exists(path)) {
                log.debug("{}文件已存在，【更新】", path.toAbsolutePath());
                Template template = CONFIGURATION.getTemplate(fileConfig.getUpdateFileName());
            } else {
                log.debug("{}文件不存在，【新建】", path.toAbsolutePath());
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                Template template = CONFIGURATION.getTemplate(fileConfig.getFileName());
                FileWriter fileWriter = new FileWriter(path.toFile());
                template.process(tableInfo, fileWriter);
            }
        }
    }

    private String coverFileName(String outputName) {
        if (outputName.contains("{{") && outputName.contains("}}")) {
            outputName = StrUtil.replace(outputName, "{{name}}", tableInfo.getName());
            outputName = StrUtil.replace(outputName, "{{originName}}", tableInfo.getOriginName());
            outputName = StrUtil.replace(outputName, "{{upperCamel}}", tableInfo.getUpperCamel());
            outputName = StrUtil.replace(outputName, "{{lowerCamel}}", tableInfo.getLowerCamel());
            outputName = StrUtil.replace(outputName, "{{underscore}}", tableInfo.getUnderscore());
        }
        return outputName;
    }
}

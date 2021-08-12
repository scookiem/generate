package org.pkh.support;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.PathUtil;
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
import java.nio.charset.Charset;
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
            Path outputPath = Paths.get(ConfigHolder.GLOBAL_CONFIG.getRootPath(), fileConfig.getOutputPath(), coverFileName(fileConfig.getOutputName()));
            /*更新*/
            if (Files.exists(outputPath)) {
                if (StrUtil.isNotBlank(fileConfig.getUpdateFileName())) {
                    log.debug("【更新】:【{}】文件已存在,有更新模板【{}】，", outputPath.toAbsolutePath(), fileConfig.getUpdateFileName());
                    tableInfo.setCustomize(getCustomize(outputPath));
                    Template template = CONFIGURATION.getTemplate(fileConfig.getUpdateFileName());
                    FileWriter fileWriter = new FileWriter(outputPath.toFile());
                    template.process(tableInfo, fileWriter);
                }
            } else {
                log.debug("【新建】:{}文件不存在，", outputPath.toAbsolutePath());
                PathUtil.mkParentDirs(outputPath);
                Template template = CONFIGURATION.getTemplate(fileConfig.getFileName());
                FileWriter fileWriter = new FileWriter(outputPath.toFile());
                template.process(tableInfo, fileWriter);
            }
        }
    }

    private String getCustomize(Path path) {
        String content = FileUtil.readString(path.toFile(), Charset.defaultCharset());
        String[] contentSplit = StrUtil.splitToArray(content, ConfigHolder.GLOBAL_CONFIG.getDelimiter());
        if (contentSplit.length == 3 && StrUtil.isNotBlank(contentSplit[1])) {
            String customize = contentSplit[1];
            customize = StrUtil.trim(customize, 1);
            customize = StrUtil.removePrefix(customize, "\r\n");
            customize = StrUtil.removeSuffix(customize, "\r\n");
            return customize;
        }
        return null;
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

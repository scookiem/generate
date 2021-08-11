package org.pkh.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Preconditions;
import lombok.Cleanup;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 配置持有人
 *
 * @author Administrator
 * @date 2021/06/13
 */
@Data
public abstract class ConfigHolder {
    /**
     * 数据库配置
     */
    public static DatabaseConfig DATABASE_CONFIG;
    /**
     * 文件配置
     */
    public static List<FileConfig> FILE_CONFIG;
    /**
     * 全局配置
     */
    public static GlobalConfig GLOBAL_CONFIG;
    /**
     * 表配置
     */
    public static TableConfig TABLE_CONFIG;

    /**
     * 初始化
     *
     * @param path 路径
     */
    public static void init(String path) throws IOException {
        String configString = getContent(path);
        JSONObject config = JSONObject.parseObject(configString);
        DATABASE_CONFIG = config.getObject("databaseConfig", DatabaseConfig.class);
        DATABASE_CONFIG.check();
        FILE_CONFIG = config.getObject("fileConfig", new TypeReference<List<FileConfig>>() {
        });
        FILE_CONFIG.forEach(FileConfig::check);
        GLOBAL_CONFIG = config.getObject("globalConfig", GlobalConfig.class);
        GLOBAL_CONFIG.check();
        TABLE_CONFIG = config.getObject("tableConfig", TableConfig.class);
        TABLE_CONFIG.check();
    }

    private static String getContent(String path) throws IOException {
        Path filePath = Paths.get(path);
        File file = filePath.toFile();
        Preconditions.checkState(file.exists(), path + "不存在");
        StringBuilder sb = new StringBuilder();
        @Cleanup
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String buffer;
        while ((buffer = bufferedReader.readLine()) != null) {
            if (!buffer.trim().startsWith("//")) {
                sb.append(buffer);
            }
        }
        return sb.toString();
    }
}

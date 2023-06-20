package org.pkh;

import cn.hutool.core.util.StrUtil;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.pkh.config.ConfigHolder;
import org.pkh.info.FieldInfo;
import org.pkh.info.TableInfo;
import org.pkh.support.FieldInfoFactory;
import org.pkh.support.FileGenerate;
import org.pkh.support.TableInfoFactory;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * 生成
 *
 * @author Administrator
 * @date 2021/06/16
 */
@Slf4j
public class Generate {
    @SneakyThrows
    public static void main(String[] args) {
        log.debug("--------开始生成--------");
        if (args == null || args.length < 1) {
            throw new RuntimeException("配置文件不能为空");
        }
        String encoding = System.getProperty("file.encoding");
        if (!encoding.equalsIgnoreCase(StandardCharsets.UTF_8.name())) {
            throw new RuntimeException(StrUtil.format("当前编码方式为[{}],为避免中文乱码,请使用指令[java -Dfile.encoding=UTF-8 -jar mbg {}]", encoding, args[0]));
        }
        ConfigHolder.init(args[0]);
        Generate generate = new Generate();
        List<TableInfo> tableInfos = generate.completionTableInfo();
        generate.generateFile(tableInfos);
        log.debug("--------生成结束--------");
    }

    /**
     * 完成表信息
     *
     * @return {@link List<TableInfo>}
     */
    @SneakyThrows
    private List<TableInfo> completionTableInfo() {
        ArrayList<TableInfo> tableResultList = new ArrayList<>();
        Class.forName(ConfigHolder.DATABASE_CONFIG.getDriverName());
        @Cleanup
        Connection connection = DriverManager.getConnection(ConfigHolder.DATABASE_CONFIG.getUrl(), ConfigHolder.DATABASE_CONFIG.getUsername(), ConfigHolder.DATABASE_CONFIG.getPassword());
        DatabaseMetaData md = connection.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", new String[]{"TABLE"});
        /*表*/
        while (rs.next()) {
            TableInfo tableInfo = TableInfoFactory.getTableInfo(rs);
            if (tableInfo != null) {
                tableResultList.add(tableInfo);
            }
        }
        /*字段*/
        for (TableInfo tableInfo : tableResultList) {
            ResultSet columns = md.getColumns(null, null, tableInfo.getOriginName(), "%");
            while (columns.next()) {
                FieldInfo fieldInfo = FieldInfoFactory.getFieldInfo(columns);
                if (fieldInfo != null) {
                    if (StrUtil.isNotEmpty(fieldInfo.getType().getPkg())) {
                        tableInfo.getImportSet().add(fieldInfo.getType().getPkg());
                    }
                    if (fieldInfo.getNullable() == 0) {
                        tableInfo.setNullable(false);
                    }
                    if ("Date".equals(fieldInfo.getType().getType())) {
                        tableInfo.setHasDate(true);
                    }
                    tableInfo.getFieldInfoList().add(fieldInfo);
                }
            }
        }
        return tableResultList;
    }

    /**
     * 生成文件
     *
     * @param tableInfos 表信息
     */
    @SneakyThrows
    private void generateFile(List<TableInfo> tableInfos) {
        ExecutorService executorService = Executors.newFixedThreadPool(tableInfos.size());
        tableInfos.forEach(tableInfo -> {
            executorService.execute(new FileGenerate(tableInfo));
        });
        executorService.shutdown();
        while (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
        }
    }
}

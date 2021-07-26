package org.pkh;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.pkh.config.ConfigHolder;
import org.pkh.info.FieldInfo;
import org.pkh.info.TableInfo;
import org.pkh.support.FileGenerate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
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
public class Generate {
    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("--------开始生成--------");
        if (args == null || args.length < 1) {
            throw new RuntimeException("配置文件不能为空");
        }
        String encoding = System.getProperty("file.encoding");
        if (!encoding.equalsIgnoreCase(StandardCharsets.UTF_8.name())) {
            System.out.printf("当前编码方式为[%s],为避免中文乱码,请使用指令[java -Dfile.encoding=UTF-8 -jar mbg %s]", encoding, args[0]);
            return;
        }
        ConfigHolder.init(args[0]);
        Generate generate = new Generate();
        List<TableInfo> tableInfos = generate.completionTableInfo();
        generate.generateFile(tableInfos);
        System.out.println("--------生成结束--------");
    }

    @SneakyThrows
    private void generateFile(List<TableInfo> tableInfos) {
        ExecutorService executorService = Executors.newFixedThreadPool(tableInfos.size());
        tableInfos.forEach(tableInfo -> {
            executorService.execute(new FileGenerate(tableInfo));
        });
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    private List<TableInfo> completionTableInfo() throws SQLException {
        ArrayList<TableInfo> tableResultList = new ArrayList<>();
        @Cleanup
        Connection connection = DriverManager.getConnection(ConfigHolder.DATABASE_CONFIG.getUrl(), ConfigHolder.DATABASE_CONFIG.getUsername(), ConfigHolder.DATABASE_CONFIG.getPassword());
        DatabaseMetaData md = connection.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", new String[]{"TABLE"});
        /*表*/
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            if (ConfigHolder.TABLE_CONFIG.getIncludeList() != null && ConfigHolder.TABLE_CONFIG.getIncludeList().size() > 0) {
                if (!ConfigHolder.TABLE_CONFIG.getIncludeList().contains(tableName)) {
                    continue;
                }
            } else if (ConfigHolder.TABLE_CONFIG.getExcludeList() != null && ConfigHolder.TABLE_CONFIG.getExcludeList().size() > 0) {
                if (ConfigHolder.TABLE_CONFIG.getExcludeList().contains(tableName)) {
                    continue;
                }
            }
            tableResultList.add(new TableInfo(tableName, rs.getString("REMARKS")));
        }
        /*字段*/
        for (TableInfo tableInfo : tableResultList) {
            ResultSet columns = md.getColumns(null, null, tableInfo.getOriginName(), "%");
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                if (ConfigHolder.TABLE_CONFIG.getExcludeColumnList() == null || !ConfigHolder.TABLE_CONFIG.getExcludeColumnList().contains(columnName)) {
                    FieldInfo fieldInfo = new FieldInfo(columnName, columns.getString("TYPE_NAME"), columns.getString("REMARKS"));
                    tableInfo.getFieldInfoList().add(fieldInfo);
                }
            }
        }
        return tableResultList;
    }
}

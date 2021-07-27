package org.pkh;

import cn.hutool.core.util.StrUtil;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.pkh.config.ConfigHolder;
import org.pkh.info.FieldInfo;
import org.pkh.info.TableInfo;
import org.pkh.support.FieldInfoFactory;
import org.pkh.support.FileGenerate;
import org.pkh.support.TableInfoFactory;

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

    /**
     * 完成表信息
     *
     * @return {@link List<TableInfo>}
     * @throws SQLException sqlexception异常
     */
    private List<TableInfo> completionTableInfo() throws SQLException {
        ArrayList<TableInfo> tableResultList = new ArrayList<>();
        @Cleanup
        Connection connection = DriverManager.getConnection(ConfigHolder.DATABASE_CONFIG.getUrl(), ConfigHolder.DATABASE_CONFIG.getUsername(), ConfigHolder.DATABASE_CONFIG.getPassword());
        DatabaseMetaData md = connection.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", new String[]{"TABLE"});
        TableInfoFactory tableInfoFactory = new TableInfoFactory();
        FieldInfoFactory fieldInfoFactory = new FieldInfoFactory();
        /*表*/
        while (rs.next()) {
            TableInfo tableInfo = tableInfoFactory.getTableInfo(rs);
            if (tableInfo != null) {
                tableResultList.add(tableInfo);
            }
        }
        /*字段*/
        for (TableInfo tableInfo : tableResultList) {
            ResultSet columns = md.getColumns(null, null, tableInfo.getOriginName(), "%");
            while (columns.next()) {
                FieldInfo fieldInfo = fieldInfoFactory.getFieldInfo(columns);
                if (fieldInfo != null) {
                    if (StrUtil.isNotEmpty(fieldInfo.getType().getPkg())) {
                        tableInfo.getImportList().add(fieldInfo.getType().getPkg());
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
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }


}

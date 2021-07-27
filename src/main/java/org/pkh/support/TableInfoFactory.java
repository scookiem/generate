package org.pkh.support;

import cn.hutool.core.collection.CollectionUtil;
import lombok.SneakyThrows;
import org.pkh.config.ConfigHolder;
import org.pkh.info.TableInfo;

import java.sql.ResultSet;

public class TableInfoFactory {
    private Process process;

    public TableInfoFactory() {
        if (CollectionUtil.isNotEmpty(ConfigHolder.TABLE_CONFIG.getIncludeTableList())) {
            process = new Process() {
                @SneakyThrows
                @Override
                public TableInfo getTableInfo(ResultSet rs) {
                    String tableName = rs.getString("TABLE_NAME");
                    if (ConfigHolder.TABLE_CONFIG.getIncludeTableList().contains(tableName)) {
                        return new TableInfo(tableName, rs.getString("REMARKS"));
                    }
                    return null;
                }
            };
        } else if (CollectionUtil.isNotEmpty(ConfigHolder.TABLE_CONFIG.getExcludeColumnList())) {
            process = new Process() {
                @SneakyThrows
                @Override
                public TableInfo getTableInfo(ResultSet rs) {
                    String tableName = rs.getString("TABLE_NAME");
                    if (ConfigHolder.TABLE_CONFIG.getExcludeTableList().contains(tableName)) {
                        return null;
                    }
                    return new TableInfo(tableName, rs.getString("REMARKS"));
                }
            };
        } else {
            process = new Process() {
                @SneakyThrows
                @Override
                public TableInfo getTableInfo(ResultSet rs) {
                    return new TableInfo(rs.getString("TABLE_NAME"), rs.getString("REMARKS"));
                }
            };
        }
    }


    @SneakyThrows
    public TableInfo getTableInfo(ResultSet rs) {
        return process.getTableInfo(rs);
    }

    private interface Process {
        TableInfo getTableInfo(ResultSet rs);
    }
}

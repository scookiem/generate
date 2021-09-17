package org.pkh.support;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.pkh.config.ConfigHolder;
import org.pkh.info.TableInfo;

import java.sql.ResultSet;

public class TableInfoFactory {
    private Process process;

    public TableInfoFactory() {
        /*处理方式优先级
         * 1、includePrefix
         * 2、includeTableList
         * 3、excludePrefix
         * 4、excludeTableList
         * */
        if (StrUtil.isNotBlank(ConfigHolder.TABLE_CONFIG.getIncludePrefix())) {
            process = new Process() {
                @SneakyThrows
                @Override
                public TableInfo getTableInfo(ResultSet rs) {
                    String tableName = rs.getString("TABLE_NAME");
                    if (StrUtil.startWith(tableName, ConfigHolder.TABLE_CONFIG.getIncludePrefix(), true)) {
                        return new TableInfo(tableName, rs.getString("REMARKS"));
                    }
                    return null;
                }
            };
            return;
        }
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
            return;
        }
        if (StrUtil.isNotBlank(ConfigHolder.TABLE_CONFIG.getExcludePrefix())) {
            process = new Process() {
                @SneakyThrows
                @Override
                public TableInfo getTableInfo(ResultSet rs) {
                    String tableName = rs.getString("TABLE_NAME");
                    if (StrUtil.startWith(tableName, ConfigHolder.TABLE_CONFIG.getExcludePrefix(), true)) {
                        return null;
                    }
                    return new TableInfo(tableName, rs.getString("REMARKS"));
                }
            };
            return;
        }
        if (CollectionUtil.isNotEmpty(ConfigHolder.TABLE_CONFIG.getExcludeColumnList())) {
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
            return;
        }
        {
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

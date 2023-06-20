package org.pkh.support;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.pkh.config.ConfigHolder;
import org.pkh.info.TableInfo;

import java.sql.ResultSet;

public abstract class TableInfoFactory {
    private Process process;

    /**
     * 获取表格信息
     * 配置优先级：
     * 1、includeTable和includePrefix
     * 2、排除excludePrefix
     * 3、排除excludeTableList
     *
     * @param rs rs
     * @return {@link TableInfo}
     */
    @SneakyThrows
    public static TableInfo getTableInfo(ResultSet rs) {
        String tableName = rs.getString("TABLE_NAME");
        if (isNeed(tableName)) {
            return new TableInfo(tableName, rs.getString("REMARKS"));
        }
        return null;
    }

    private static boolean isNeed(String tableName) {
        /*符合前缀或者list名字*/
        if ((StrUtil.isNotBlank(ConfigHolder.TABLE_CONFIG.getIncludePrefix()) && StrUtil.startWith(tableName, ConfigHolder.TABLE_CONFIG.getIncludePrefix(), true)) || ConfigHolder.TABLE_CONFIG.getIncludeTableList().contains(tableName)) {
            /*排除符合前缀或者list名字*/
            if ((StrUtil.isNotBlank(ConfigHolder.TABLE_CONFIG.getExcludePrefix()) && StrUtil.startWith(tableName, ConfigHolder.TABLE_CONFIG.getExcludePrefix(), true)) || ConfigHolder.TABLE_CONFIG.getExcludeTableList().contains(tableName)) {
                return false;
            }
            return true;
        }
        return false;
    }
}


package org.pkh.database;

import org.pkh.info.TableInfo;

import java.util.List;


/**
 * idatabase查询
 *
 * @author Administrator
 * @date 2021/06/13
 */
public abstract class IDatabaseQuery {
    /**
     * 查询表
     *
     * @return {@link List<TableInfo>}
     */
    public abstract List<TableInfo> queryTables();
}

package org.pkh.config;

import lombok.Data;

/**
 * 数据库配置
 *
 * @author Administrator
 * @date 2021/06/09
 */
@Data
public class DatabaseConfig {
    /**
     * 驱动名
     */
    private String driverName;
    /**
     * url
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}

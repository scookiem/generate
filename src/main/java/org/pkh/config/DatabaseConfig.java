package org.pkh.config;

import lombok.Data;
import org.pkh.Required;

/**
 * 数据库配置
 *
 * @author Administrator
 * @date 2021/06/09
 */
@Data
public class DatabaseConfig extends AbstractConfig {
    /**
     * 驱动名
     */
    @Required(message = "driverName不能为空")
    private String driverName;
    /**
     * url
     */
    @Required(message = "url不能为空")
    private String url;
    /**
     * 用户名
     */
    @Required(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @Required(message = "密码不能为空")
    private String password;
}

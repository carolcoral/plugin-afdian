package site.xindu.afdian.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BaseSettingConfig {

    public static final String CONFIG_MAP_NAME = "plugin-afdian-config";
    public static final String GROUP = "account";

    /**
     * 用户token
     */
    private String token = "";

    /**
     * 用户ID
     */
    private String userId = "";

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 登录用户密码
     */
    private String password;

}

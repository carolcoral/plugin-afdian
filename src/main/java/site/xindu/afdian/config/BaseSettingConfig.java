package site.xindu.afdian.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BaseSettingConfig {

    public static final String CONFIG_MAP_NAME = "plugin-afdian-config";
    public static final String GROUP = "basic";

    /**
     * 用户token
     */
    private String token = "";

    /**
     * 用户ID
     */
    private String userId = "";

    /**
     * 赞助地址
     */
    private String sponsorUrl = "";

    /**
     * 切换赞助金额显示颜色值
     */
    private Double sponsorNumber = 66.00;


}

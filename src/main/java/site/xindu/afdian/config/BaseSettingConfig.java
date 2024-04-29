package site.xindu.afdian.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BaseSettingConfig {

    public static final String CONFIG_MAP_NAME = "plugin-afdian-config";
    public static final String GROUP = "basic";

    private String token = "";
    private String userId = "";

}

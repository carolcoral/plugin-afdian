package site.xindu.afdian.config;

import lombok.Data;
import lombok.ToString;

/**
 * 赞赏展示页2设置
 */
@Data
@ToString
public class ThemeSettingConfig {

    public static final String CONFIG_MAP_NAME = "plugin-afdian-config";
    public static final String GROUP = "themeSetting";

    /**
     * 赞助展示页面
     */
    private String themeStyle = "afdian";

    /**
     * 顶部图片文字
     */
    private String rewardTopTitle = "";

    // 样式2

    /**
     * 启用顶部头像滚动
     */
    private Boolean enableRewardRoll = false;

    /**
     * 顶部图片URL
     */
    private String rewardTopImgUrl = "";

    // 样式3

    private Boolean enableTheme3Img = false;

    private String theme3ImgUrl = "";
}

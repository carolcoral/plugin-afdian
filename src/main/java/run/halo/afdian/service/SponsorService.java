package run.halo.afdian.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import run.halo.afdian.config.BaseSettingConfig;
import run.halo.afdian.entity.SponsorEntity;
import run.halo.afdian.utils.EncryptUtils;
import java.util.Calendar;

@Service
@Slf4j
public class SponsorService {

    private final DefaultSettingFetcher settingFetcher;

    public SponsorService(DefaultSettingFetcher settingFetcher) {
        this.settingFetcher = settingFetcher;
    }

    /**
     * 获取第一页的爱发电赞助用户
     *
     * @return 赞助用户详情 {@link SponsorEntity}
     */
    public SponsorEntity getSponsorList() {

        BaseSettingConfig baseSettingConfig =
            settingFetcher.fetch(
                    BaseSettingConfig.CONFIG_MAP_NAME,
                    BaseSettingConfig.GROUP,
                    BaseSettingConfig.class)
                .orElseGet(BaseSettingConfig::new);
        String url = "https://afdian.net/api/open/query-sponsor";
        JSONObject params = new JSONObject();
        var timeInMillis = Calendar.getInstance().getTimeInMillis() / 1000;

        var userId = baseSettingConfig.getUserId();
        // 123params{"a":333}ts1624339905user_idabc
        var sign = baseSettingConfig.getToken().concat("params{\"page\":1}ts")
            .concat(String.valueOf(timeInMillis)).concat("user_id").concat(userId);

        String signMd5 = EncryptUtils.encrypt32(sign);

        params.set("user_id", userId);
        params.set("params", "{\"page\":1}");
        params.set("ts", timeInMillis);
        params.set("sign", signMd5);

        var execute = HttpRequest.post(url).body(params.toString()).execute();
        var body = execute.body();
        SponsorEntity sponsorEntity = new SponsorEntity();
        BeanUtils.copyProperties(body, sponsorEntity, SponsorEntity.class);
        log.info(sponsorEntity.toString());
        return sponsorEntity;
    }

}

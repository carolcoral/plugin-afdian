package run.halo.afdian.service;

import com.nimbusds.jose.shaded.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.halo.afdian.config.BaseSettingConfig;
import run.halo.afdian.entity.SponsorEntity;
import run.halo.afdian.utils.EncryptUtils;
import run.halo.afdian.utils.RequestUtils;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SponsorService {

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private BaseSettingConfig baseSettingConfig;

    /**
     * 获取第一页的爱发电赞助用户
     * @return 赞助用户详情 {@link SponsorEntity}
     */
    public SponsorEntity getSponsorList(){
        String url = "https://afdian.net/api/open/query-sponsor";
        Map<String, Object> params = new HashMap<>();
        var timeInMillis = Calendar.getInstance().getTimeInMillis() / 1000;

        var userId = baseSettingConfig.getUserId();
        // 123params{"a":333}ts1624339905user_idabc
        var sign = baseSettingConfig.getToken().concat("params{\"page\":1}ts")
            .concat(String.valueOf(timeInMillis)).concat("user_id").concat(userId);

        String signMd5 = EncryptUtils.encrypt32(sign);

        params.put("user_id", userId);
        params.put("params", "{\"page\":1}");
        params.put("ts", timeInMillis);
        params.put("sign", signMd5);
        return requestUtils.post(params, url, SponsorEntity.class);
    }

}

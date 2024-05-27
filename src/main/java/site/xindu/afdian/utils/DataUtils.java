package site.xindu.afdian.utils;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;
import run.halo.app.infra.utils.JsonUtils;
import site.xindu.afdian.entity.SponsorEntity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {

    public static Mono<JsonNode> changePayTime(Mono<JsonNode> mono) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mono.map(res->{
            var sponsorEntity = JsonUtils.jsonToObject(res.asText(), SponsorEntity.class);
            var dataList = sponsorEntity.getData().getList();
            for (SponsorEntity.SponsorJsonData sponsorJsonData : dataList) {
                var firstPayTime = sponsorJsonData.getFirst_pay_time();
                var firstDateString = simpleDateFormat.format(new Date(firstPayTime));
                sponsorJsonData.setFirstPayTime(firstDateString);
                var lastPayTime = sponsorJsonData.getLast_pay_time();
                var lastDateString = simpleDateFormat.format(new Date(lastPayTime));
                sponsorJsonData.setFirstPayTime(lastDateString);
            }
            return res;
        });
        return mono;
    }
}

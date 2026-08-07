package site.xindu.afdian.utils;

import reactor.core.publisher.Mono;
import site.xindu.afdian.entity.SponsorEntity;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {

    private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();

    public static Mono<JsonNode> changePayTime(Mono<JsonNode> mono) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return mono.map(res -> {
            try {
                var sponsorEntity = JSON_MAPPER.treeToValue(res, SponsorEntity.class);
                if (sponsorEntity.getData() == null || sponsorEntity.getData().getList() == null) {
                    return res;
                }
                var dataList = sponsorEntity.getData().getList();
                for (SponsorEntity.SponsorJsonData sponsorJsonData : dataList) {
                    var firstPayTime = sponsorJsonData.getFirst_pay_time();
                    var firstDateString = simpleDateFormat.format(new Date(firstPayTime));
                    sponsorJsonData.setFirstPayTime(firstDateString);
                    var lastPayTime = sponsorJsonData.getLast_pay_time();
                    var lastDateString = simpleDateFormat.format(new Date(lastPayTime));
                    sponsorJsonData.setLastPayTime(lastDateString);
                }
                return res;
            } catch (Exception e) {
                throw new RuntimeException("解析赞助者数据出现异常!", e);
            }
        });
    }
}

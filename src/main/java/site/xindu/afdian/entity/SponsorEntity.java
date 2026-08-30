package site.xindu.afdian.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SponsorEntity {

    private int ec;

    private String em;

    private SponsorData data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SponsorData{
        private int total_count;
        private int total_page;
        private List<SponsorJsonData> list;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SponsorJsonData{
        private List<Object> sponsor_plans;
        private Double all_sum_amount;
        private Long first_pay_time;
        private Long last_pay_time;
        private String firstPayTime;
        private String lastPayTime;
        private User user;
        private Long user_private_id;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User{
        private String user_id;
        private String name;
        private String avatar;
    }

}

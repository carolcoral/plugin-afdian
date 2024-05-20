package site.xindu.afdian.entity;

import lombok.Data;
import java.util.List;

@Data
public class SponsorEntity {

    private int ec;

    private String em;

    private SponsorData data;

    @Data
    public static class SponsorData{
        private int total_count;
        private int total_page;
        private List<SponsorJsonData> list;
    }

    @Data
    public static class SponsorJsonData{
        private List<Object> sponsor_plans;
        private User user;
    }

    @Data
    public static class User{
        private String user_id;
        private String name;
        private String avatar;
    }

}

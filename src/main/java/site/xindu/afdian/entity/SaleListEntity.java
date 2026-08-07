package site.xindu.afdian.entity;

import java.util.List;
import lombok.Data;

/**
 * 爱发电赞助方案/商品列表（/api/creator/get-plans 响应）
 */
@Data
public class SaleListEntity {

    private int ec;

    private String em;

    private SaleListData data;

    @Data
    public static class SaleListData {

        private List<Sale> sale_list;
    }

    @Data
    public static class Sale {

        private String plan_id;

        private String pic;

        private String name;

        private String desc;

        private String price;

        private String vip_price;

        private int has_time_limit_price;
    }
}

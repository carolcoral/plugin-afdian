package site.xindu.afdian.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

class SponsorEntityTest {

    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    @Test
    void shouldIgnoreRequestMetadataInEmptySponsorResponse() throws Exception {
        var response = jsonMapper.readTree("""
            {
              "ec": 200,
              "em": "sponsor",
              "data": {
                "total_count": 0,
                "total_page": 0,
                "list": [],
                "request": {
                  "page": 1
                }
              }
            }
            """);

        var sponsor = jsonMapper.treeToValue(response, SponsorEntity.class);

        assertEquals(200, sponsor.getEc());
        assertNotNull(sponsor.getData());
        assertEquals(0, sponsor.getData().getTotal_count());
        assertTrue(sponsor.getData().getList().isEmpty());
    }

    @Test
    void shouldIgnoreFutureFieldsInNestedSponsorObjects() throws Exception {
        var response = jsonMapper.readTree("""
            {
              "ec": 200,
              "em": "sponsor",
              "future_top_level": true,
              "data": {
                "total_count": 1,
                "total_page": 1,
                "future_data_field": "ignored",
                "list": [
                  {
                    "sponsor_plans": [],
                    "all_sum_amount": 10,
                    "first_pay_time": 1700000000,
                    "last_pay_time": 1700000000,
                    "future_sponsor_field": "ignored",
                    "user": {
                      "user_id": "example-user",
                      "name": "Example",
                      "avatar": "",
                      "future_user_field": "ignored"
                    }
                  }
                ]
              }
            }
            """);

        var sponsor = jsonMapper.treeToValue(response, SponsorEntity.class);

        assertEquals(1, sponsor.getData().getList().size());
        assertEquals("Example", sponsor.getData().getList().getFirst().getUser().getName());
    }
}

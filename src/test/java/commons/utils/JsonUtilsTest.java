package commons.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;

public class JsonUtilsTest {
    @Test
    public void testToParamMap() {
        Event test = Event.builder().eventId(1).name("").build();
        Map<String, Object> map = JsonUtil.toParamMap(test);
        Assertions.assertNull(map.get("name"));
    }

    @Test
    public void testToJsonString() {
        Event test = Event.builder().eventId(1).build();
        Assertions.assertEquals("{\"eventId\":1}", JsonUtil.toJsonString(test));
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Event {
        Integer eventId;
        String name;
        Date createTime;
    }
}

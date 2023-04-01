package commons.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class FluentMapTest {
    @Test
    public void test() {
        Integer v1 = 1;
        FluentMap<String, Integer> map = new FluentMap<>();
        map.putIfNotNull("t1", v1).putIfNotNull("t2", null);
        Assertions.assertEquals(map.get("t1"), v1);
        Assertions.assertFalse(map.containsKey("t2"));

        Map<String, String> strMap = map.toStringMap();
        Assertions.assertEquals(String.valueOf(v1), strMap.get("t1"));
    }
}

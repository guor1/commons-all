package commons.utils;

import org.junit.Assert;
import org.junit.Test;

public class TestLRUCache {
    @Test
    public void test() {
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.put("a", "1");
        cache.put("b", "2");
        cache.put("c", "3");
        cache.put("d", "4");
        Assert.assertFalse(cache.containsKey("a"));
    }
}

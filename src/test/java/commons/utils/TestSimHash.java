package commons.utils;

import org.junit.Assert;
import org.junit.Test;

public class TestSimHash {
    @Test
    public void test() {
        String s1 = "I'm very happy";
        String s2 = "i'm very happy";
        String s3 = "今天天气真好";
        Assert.assertEquals(5, SimHash.distance(s1, s2));
        Assert.assertTrue(SimHash.distance(s1, s3) > 0);
    }
}

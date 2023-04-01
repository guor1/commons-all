package commons.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {
    @Test
    public void testIsNotBlank() {
        Assertions.assertTrue(StringUtils.isNotBlank("a"));
    }

    @Test
    public void testCleanEmptyStr() {
        String s1 = StringUtils.cleanEmptyStr("a b");
        Assertions.assertEquals("ab", s1);
    }

    @Test
    public void testTrimEmptyStr() {
        String s1 = StringUtils.trimEmptyStr("a b  ");
        Assertions.assertEquals("a b", s1);
    }
}

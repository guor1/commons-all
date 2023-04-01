package commons.utils;

import commons.TestApp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest(classes = TestApp.class)
public class AppUtilsTest {

    @Test
    public void testContainsBean() {
        Assertions.assertTrue(AppUtils.containsBean("passwordEncoder"));
    }

    @Test
    public void testIsSingleton() {
        Assertions.assertTrue(AppUtils.isSingleton("passwordEncoder"));
    }

    @Test
    public void testGetType() {
        Assertions.assertEquals(BCryptPasswordEncoder.class, AppUtils.getType("passwordEncoder"));
    }

    @Test
    public void testGetBean() {
        PasswordEncoder passwordEncoder1 = AppUtils.getBean(PasswordEncoder.class);
        Assertions.assertTrue(passwordEncoder1 instanceof BCryptPasswordEncoder);

        PasswordEncoder passwordEncoder2 = AppUtils.getBean("passwordEncoder", PasswordEncoder.class);
        Assertions.assertTrue(passwordEncoder2 instanceof BCryptPasswordEncoder);

        Object passwordEncoder3 = AppUtils.getBean("passwordEncoder");
        Assertions.assertTrue(passwordEncoder3 instanceof BCryptPasswordEncoder);
    }
}

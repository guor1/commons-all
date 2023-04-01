package commons.utils;

import commons.model.PageVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

@SpringBootTest
public class WebUtilsTest {

    @Test
    public void testDefaultPageable() {
        PageVO pageVO = WebUtils.defaultPageVo();
        Assertions.assertEquals(WebUtils.DEF_PAGE_NO, pageVO.getPageNo());
        Assertions.assertEquals(WebUtils.DEF_PAGE_SIZE, pageVO.getPageSize());
    }

    @Test
    public void testGetResponse() {
        HttpServletResponse response = WebUtils.getResponse();
        Assertions.assertTrue(response instanceof MockHttpServletResponse);
    }
}

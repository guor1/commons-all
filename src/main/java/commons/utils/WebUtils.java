package commons.utils;

import commons.model.PageVO;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author guorui1
 */
public class WebUtils {
    @NonNull
    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }

    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }


    public static int getIntParameter(String name, int def) {
        return parseInt(getRequest().getParameter(name), def);
    }

    public static final String RE_INT = "^[-+]?\\d+$";

    public static int parseInt(String s, int def) {
        if (s != null && s.matches(RE_INT)) {
            return Integer.parseInt(s);
        }
        return def;
    }

    /**
     * 通用默认翻页请求，读取HttpServletRequest中的pageNo和pageSize
     */
    public static PageVO defaultPageVo() {
        int pageNo = getIntParameter("pageNo", DEF_PAGE_NO);
        int pageSize = getIntParameter("pageSize", DEF_PAGE_SIZE);

        if (pageNo < 0) {
            pageNo = DEF_PAGE_NO;
        }
        if (pageSize < 0) {
            pageSize = DEF_PAGE_SIZE;
        }
        return PageVO.builder().pageNo(pageNo).pageSize(pageSize).build();
    }

    public static final int DEF_PAGE_NO = 1;
    public static final int DEF_PAGE_SIZE = 10;
}

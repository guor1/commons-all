package commons.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class ServletUtils {
    public static String getParameter(HttpServletRequest request, String paramName) {
        return getParameter(request, paramName, null);
    }

    public static String getParameter(HttpServletRequest request, String paramName, String defaultValue) {
        String value = request.getParameter(paramName);
        return value == null ? defaultValue : value;
    }

    public static HttpSession getSession(HttpServletRequest request) {
        return getSession(request, true);
    }

    public static HttpSession getSession(HttpServletRequest request, boolean autoCreate) {
        return request.getSession(autoCreate);
    }

    public static Object getSessionAttribute(HttpServletRequest request, String attrName) {
        return getSessionAttribute(request, attrName, null);
    }

    public static Object getSessionAttribute(HttpServletRequest request, String attrName, Object defaultValue) {
        Object value = getSession(request).getAttribute(attrName);
        return value == null ? defaultValue : value;
    }

    public static void setSessionAttribute(HttpServletRequest request, String attrName, Object value) {
        if (value == null) {
            getSession(request, false).removeAttribute(attrName);
        } else {
            getSession(request, true).setAttribute(attrName, value);
        }
    }

    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies[0] != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    @SneakyThrows
    public static PrintWriter getWriter(HttpServletResponse response) {
        return response.getWriter();
    }

    public static void write(HttpServletResponse response, String output) {
        getWriter(response).write(output);
    }

    public static void writeJson(HttpServletResponse response, String output) {
        setContentType(response, CT_JSON_RFC4627);
        write(response, output);
    }

    public static JSONObject getRequestJson(HttpServletRequest request) throws IOException {
        String req = IoUtil.toString(request.getReader());
        return JSON.parseObject(req);
    }

    public static void setContentType(ServletResponse response, String contentType) {
        String encod = response.getCharacterEncoding();
        encod = (StringUtils.isBlank(encod)) ? "UTF-8" : encod;
        response.setContentType(contentType + "; charset=" + encod);
    }

    public static void setNoCacheHeaders(HttpServletResponse response) {
        // Set to expire far in the past.
        response.setHeader("expires", "Sat, 6 May 1995 12:00:00 GMT");
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("cache-control", "no-cache,no-store,max-age=0");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("cache-control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("pragma", "no-cache");
    }

    public static String getReferer(HttpServletRequest request) {
        return request.getHeader("referer");
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String hv = request.getHeader("x-requested-with");
        return "XMLHttpRequest".equalsIgnoreCase(hv);
    }

    /**
     * 添加缓存头（到期时间、max-age）
     */
    public static void addCacheHead(HttpServletResponse response, int minute) {
        response.setHeader("Cache-Control", "public, max-age=" + (minute * 60));
        response.setDateHeader("Expires", DateUtils.add(DateUtils.now(), Calendar.MINUTE, minute).getTime());
    }

    // -----------------------------------------------------------------------------------

    public static final String INCLUDE_REQUEST_URI = "javax.servlet.include.request_uri";
    public static final String INCLUDE_CONTEXT_PATH = "javax.servlet.include.context_path";
    public static final String INCLUDE_SERVLET_PATH = "javax.servlet.include.servlet_path";
    public static final String INCLUDE_PATH_INFO = "javax.servlet.include.path_info";
    public static final String INCLUDE_QUERY_STRING = "javax.servlet.include.query_string";

    public static final String FORWARD_REQUEST_URI = "javax.servlet.forward.request_uri";
    public static final String FORWARD_CONTEXT_PATH = "javax.servlet.forward.context_path";
    public static final String FORWARD_SERVLET_PATH = "javax.servlet.forward.servlet_path";
    public static final String FORWARD_PATH_INFO = "javax.servlet.forward.path_info";
    public static final String FORWARD_QUERY_STRING = "javax.servlet.forward.query_string";

    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";

    public static final String JSP_JSP_EXCEPTION = "javax.servlet.jsp.jspException";

    public static final String CT_PLAIN = "text/plain";
    public static final String CT_HTML = "text/html";
    public static final String CT_XML = "text/xml";
    public static final String CT_JS = "text/javascript";
    public static final String CT_JS_RFC4392 = "application/javascript";
    public static final String CT_JSON = CT_JS;
    public static final String CT_JSON_RFC4627 = "application/json";
}

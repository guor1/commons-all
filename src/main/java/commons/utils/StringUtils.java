package commons.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author guorui1
 */
public class StringUtils {
    /**
     * 各种空白字符
     */
    private static final String EMPTY_CHARS = "\u0020\u00A0\uFEFF\u3000\b\t\n\r\f";
    public static final String EMPTY = "";

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 删除字符串中的所有空白字符
     */
    public static String cleanEmptyStr(String str) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!isEmptyChar(c)) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * 是否空白字符
     */
    public static boolean isEmptyChar(char ch) {
        return EMPTY_CHARS.indexOf(ch) != -1;
    }

    /**
     * 删除字符串两端空白字符，替换string原有的trim方法，新增其他空白字符
     */
    public static String trimEmptyStr(String str) {
        int len = str.length();
        int st = 0;
        while ((st < len) && (isEmptyChar(str.charAt(st)))) {
            st++;
        }
        while ((st < len) && (isEmptyChar(str.charAt(len - 1)))) {
            len--;
        }
        return str.substring(st, len);
    }

    public static String encodeFormFields(String content) {
        return encodeFormFields(content, StandardCharsets.UTF_8);
    }

    public static String encodeFormFields(String content, Charset charset) {
        try {
            return URLEncoder.encode(content, charset.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 在一个字符串中匹配符合条件的子串
     */
    public static List<String> match(String content, String regex) {
        List<String> contentList = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String group = matcher.group(1);
            if (!contentList.contains(group)) {
                contentList.add(group);
            }
        }
        return contentList;
    }
}

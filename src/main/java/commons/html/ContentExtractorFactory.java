package commons.html;

import commons.html.impl.*;
import commons.utils.StringUtils;

/**
 * @author guorui1
 */
public class ContentExtractorFactory {

    private static final char EXP_SPLIT = ':';

    public static ContentExtractor create(String expression) {
        if (StringUtils.isEmpty(expression)) {
            throw new IllegalArgumentException("表达式不能为空.");
        }
        int ch = expression.indexOf(EXP_SPLIT);
        if (ch == -1) {
            throw new IllegalArgumentException("无法解析表达式类型.");
        }
        if (expression.contains(ContentExtractor.JOIN)) {
            return new ComplexContentExtractor(expression);
        }
        String exp = expression.substring(ch + 1);
        char c = expression.charAt(0);
        switch (c) {
            case 'i':
                return new IdContentExtractor(exp);
            case 'c':
                return new ClassContentExtractor(exp);
            case 't':
                return new TagContentExtractor(exp);
            case 'r':
                return new RegexContentExtractor(exp);
            case 's':
                return new SelectorContentExtractor(exp);
            default:
                throw new IllegalArgumentException("未知表达式类型.");
        }
    }
}

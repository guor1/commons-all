package commons.html.impl;


import commons.html.ContentExtractor;
import commons.utils.StringUtils;
import lombok.Data;

import java.util.List;

/**
 * @author guorui1
 */
@Data
public class RegexContentExtractor implements ContentExtractor {

    private String expression;

    public RegexContentExtractor(String expression) {
        this.expression = expression;
    }

    @Override
    public String extract(String html) {
        List<String> matches = StringUtils.match(html, getExpression());
        if (matches.isEmpty()) {
            return null;
        }
        return matches.get(0);
    }
}

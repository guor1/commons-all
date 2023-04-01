package commons.html.impl;

import commons.html.ContentExtractor;
import commons.html.ContentExtractorFactory;
import commons.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 复杂化的正文抽取器
 *
 * @author guor
 */
public class ComplexContentExtractor implements ContentExtractor {

    List<ContentExtractor> extractors = new ArrayList<>();

    public ComplexContentExtractor(String expression) {
        if (expression.contains(JOIN)) {
            String[] ctx = expression.split("\\+");
            for (String exp : ctx) {
                extractors.add(ContentExtractorFactory.create(exp));
            }
        }
    }

    @Override
    public String extract(String html) {
        for (ContentExtractor extractor : extractors) {
            String content = extractor.extract(html);
            if (!StringUtils.isEmpty(content)) {
                return content;
            }
        }
        return null;
    }
}

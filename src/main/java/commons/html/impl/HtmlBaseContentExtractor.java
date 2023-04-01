package commons.html.impl;

import commons.html.ContentExtractor;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;

/**
 * @author guorui1
 */
@Data
public abstract class HtmlBaseContentExtractor implements ContentExtractor {

    private String expression;

    public HtmlBaseContentExtractor(String expression) {
        this.expression = expression;
    }

    @Override
    public String extract(String html) {
        Document dirtyDocument = Jsoup.parse(html);
        Element element = new Cleaner(Safelist.basic()).clean(dirtyDocument);
        Elements elements = element.select(selector());
        return elements.isEmpty() ? null : elements.text();
    }

    /**
     * 选择器
     *
     * @return 返回常见选择器表达式
     */
    public abstract String selector();
}

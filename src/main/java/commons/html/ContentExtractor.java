package commons.html;

/**
 * 正文抽取器接口，定义如下四种表达方式
 * <p>
 * 1. 基于ID（i:content）
 * <p>
 * 2. 基于CLASS(c:content)
 * <p>
 * 3. 基于标签(t:article)
 * <p>
 * 4. 基于正则表达式(r:&lt;div&gt;(.*)&lt;/div&gt;)
 * <p>
 * 5. 复杂抽取器i:content+c:content
 * <p>
 * 6. 选择器抽取器s:#Cnt-Main-Article-QQ p
 *
 * @author guor
 */
public interface ContentExtractor {
    String JOIN = "+";

    /**
     * 基于规则的正文抽取器
     *
     * @param html html原文
     * @return 返回抽取的正文
     */
    String extract(String html);
}

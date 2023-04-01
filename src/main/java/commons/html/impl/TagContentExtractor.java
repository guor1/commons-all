package commons.html.impl;

/**
 * @author guorui1
 */
public class TagContentExtractor extends HtmlBaseContentExtractor {

    public TagContentExtractor(String expression) {
        super(expression);
    }

    @Override
    public String selector() {
        return getExpression();
    }
}

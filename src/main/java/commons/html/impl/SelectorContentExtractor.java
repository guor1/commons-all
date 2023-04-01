package commons.html.impl;


/**
 * @author guorui1
 */
public class SelectorContentExtractor extends HtmlBaseContentExtractor {

    public SelectorContentExtractor(String expression) {
        super(expression);
    }

    @Override
    public String selector() {
        return getExpression();
    }
}

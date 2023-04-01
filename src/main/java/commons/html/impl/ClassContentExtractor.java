package commons.html.impl;

/**
 * @author guorui1
 */
public class ClassContentExtractor extends HtmlBaseContentExtractor {

    public ClassContentExtractor(String expression) {
        super(expression);
    }

    @Override
    public String selector() {
        return "." + getExpression();
    }
}

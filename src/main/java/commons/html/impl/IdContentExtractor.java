package commons.html.impl;

/**
 * @author guorui1
 */
public class IdContentExtractor extends HtmlBaseContentExtractor {

	public IdContentExtractor(String expression) {
		super(expression);
	}

	@Override
	public String selector() {
		return "#" + getExpression();
	}
}

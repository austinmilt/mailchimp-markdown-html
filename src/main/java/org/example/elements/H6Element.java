package org.example.elements;

/**
 * Markdown equivalent of HTML {@code h6}
 */
public class H6Element implements MarkdownElement {
    private static final H6Element INSTANCE = new H6Element();

    private final String openingTag = "<h6>";
    private final String closingTag = "</h6>";

    private H6Element() {
        // no inner content to handle
    };

    public static H6Element of() {
        return INSTANCE;
    }

    @Override
    public String toHtmlOpening() {
        return openingTag;
    }

    @Override
    public String toHtmlClosing() {
        return closingTag;
    }
}

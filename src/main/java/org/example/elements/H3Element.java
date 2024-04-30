package org.example.elements;

/**
 * Markdown equivalent of HTML {@code h3}
 */
public class H3Element implements MarkdownElement {
    private static final H3Element INSTANCE = new H3Element();

    private final String openingTag = "<h3>";
    private final String closingTag = "</h3>";

    private H3Element() {
        // no inner content to handle
    };

    public static H3Element of() {
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

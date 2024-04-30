package org.example.elements;

/**
 * Markdown equivalent of HTML {@code h4}
 */
public class H4Element implements MarkdownElement {
    private static final H4Element INSTANCE = new H4Element();

    private final String openingTag = "<h4>";
    private final String closingTag = "</h4>";

    private H4Element() {
        // no inner content to handle
    };

    public static H4Element of() {
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

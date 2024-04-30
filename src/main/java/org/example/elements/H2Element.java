package org.example.elements;

/**
 * Markdown equivalent of HTML {@code h2}
 */
public class H2Element implements MarkdownElement {
    private static final H2Element INSTANCE = new H2Element();

    private final String openingTag = "<h2>";
    private final String closingTag = "</h2>";

    private H2Element() {
        // no inner content to handle
    };

    public static H2Element of() {
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

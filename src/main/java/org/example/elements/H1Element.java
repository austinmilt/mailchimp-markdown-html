package org.example.elements;

/**
 * Markdown equivalent of HTML {@code h1}
 */
public class H1Element implements MarkdownElement {
    private static final H1Element INSTANCE = new H1Element();

    private final String openingTag = "<h1>";
    private final String closingTag = "</h1>";

    private H1Element() {
        // no inner content to handle
    };

    public static H1Element of() {
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

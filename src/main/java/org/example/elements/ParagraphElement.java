package org.example.elements;

/**
 * Markdown equivalent of HTML {@code p}
 */
public class ParagraphElement implements MarkdownElement {
    private static final ParagraphElement INSTANCE = new ParagraphElement();

    private final String openingTag = "<p>";
    private final String closingTag = "</p>";

    private ParagraphElement() {
        // no inner content to handle
    };

    public static ParagraphElement of() {
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

package org.example.elements;

/**
 * Markdown equivalent of HTML {@code h5}
 */
public class H5Element implements MarkdownElement {
    private static final H5Element INSTANCE = new H5Element();

    private final String openingTag = "<h5>";
    private final String closingTag = "</h5>";

    private H5Element() {
        // no inner content to handle
    };

    public static H5Element of() {
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

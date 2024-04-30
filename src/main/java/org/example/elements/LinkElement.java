package org.example.elements;

/**
 * Markdown equivalent of HTML {@code a}
 */
public class LinkElement implements MarkdownElement {
    private final String opening;
    private final String closing = "</a>";

    private LinkElement(String opening) {
        this.opening = opening;
    };

    public static LinkElement of(String url) {
        return new LinkElement(String.format("<a href=\"%s\">", url));
    }

    @Override
    public String toHtmlOpening() {
        return opening;
    }

    @Override
    public String toHtmlClosing() {
        return closing;
    }
}

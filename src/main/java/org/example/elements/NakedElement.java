package org.example.elements;

/**
 * Inner content of another element, e.g.
 * in {@code [with a link](http://google.com)} this would be
 * {@code http://google.com}.
 */
public class NakedElement implements MarkdownElement {
    private final String content;

    private NakedElement(String content) {
        this.content = content;
    }

    public static NakedElement of(String content) {
        return new NakedElement(content);
    }

    @Override
    public String toHtmlOpening() {
        return content;
    }

    @Override
    public String toHtmlClosing() {
        return "";
    }

}

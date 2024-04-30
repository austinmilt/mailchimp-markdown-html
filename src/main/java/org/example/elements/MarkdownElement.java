package org.example.elements;

/**
 * Markdown elements that know how to convert themselves to proper HTML
 * elements.
 */
public interface MarkdownElement {
    /**
     * @return the opening HTML for this element without any children or content,
     *         e.g. {@code <a href="www.google.com">}
     */
    String toHtmlOpening();

    /**
     * @return the closing HTML for this element without closing for children, e.g.
     *         {@code </a>}
     */
    String toHtmlClosing();
}

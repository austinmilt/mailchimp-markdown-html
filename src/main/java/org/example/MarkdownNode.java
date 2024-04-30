package org.example;

import java.util.ArrayList;
import java.util.List;
import org.example.elements.LinkElement;
import org.example.elements.MarkdownElement;
import org.example.elements.NakedElement;
import jakarta.annotation.Nullable;

/**
 * Tree node for storing and recursively converting nested markdown elements to HTML.
 */
class MarkdownNode {
    private final MarkdownElement outerElement;
    private final List<MarkdownNode> children = new ArrayList<>();

    private MarkdownNode(@Nullable MarkdownElement outerElement) {
        this.outerElement = outerElement;
    }

    public static MarkdownNode root() {
        return new MarkdownNode(null);
    }

    /**
     * Create a node with an outer element with a single child that is a string
     * 
     * @param element outer element (e.g. h1 tag)
     * @param content content of the outer element (e.g. h1 text)
     * @return
     */
    public static MarkdownNode simple(MarkdownElement element, String content) {
        final MarkdownNode result = new MarkdownNode(element);
        result.children.add(new MarkdownNode(NakedElement.of(content)));
        return result;
    }

    /**
     * Create a node for a markdown link
     * 
     * @param linkText text to be displayed in the link
     * @param urlText href/url to which the link should navigate
     * @return
     */
    public static MarkdownNode link(String linkText, String urlText) {
        final MarkdownNode result = new MarkdownNode(LinkElement.of(urlText));
        result.children.add(new MarkdownNode(NakedElement.of(linkText)));
        return result;
    }

    public void addChild(MarkdownNode child) {
        children.add(child);
    }

    /**
     * Recursively adds self and children to an output HTML string via (implicit) BFS.
     * 
     * @param htmlBuilder output HTML to write to
     */
    public void toHtml(StringBuilder htmlBuilder) {
        if (outerElement != null) {
            htmlBuilder.append(outerElement.toHtmlOpening());
        }
        for (MarkdownNode child : children) {
            // put newlines between HTML sections to make it easier to read
            if ((child.outerElement != null) && !(child.outerElement instanceof NakedElement)) {
                htmlBuilder.append("\n\n");
            }
            child.toHtml(htmlBuilder);
        }
        if (outerElement != null) {
            htmlBuilder.append(outerElement.toHtmlClosing());
        }
    }
}

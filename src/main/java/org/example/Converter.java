package org.example;

import java.util.ArrayList;
import java.util.List;

import org.example.elements.H1Element;
import org.example.elements.H2Element;
import org.example.elements.H3Element;
import org.example.elements.H4Element;
import org.example.elements.H5Element;
import org.example.elements.H6Element;
import org.example.elements.LinkElement;
import org.example.elements.MarkdownElement;
import org.example.elements.NakedElement;
import org.example.elements.ParagraphElement;

import jakarta.annotation.Nullable;

public class Converter {

    /**
     * Converts a complete markdown document string to a complete HTML document
     * string.
     * 
     * @param markdownDocument markdown document to be converted
     * @return converted HTML document
     */
    public String convertDocument(String markdownDocument) {
        return String.format("""
                <!DOCTYPE html>
                <html lang="en">
                  <head>
                    <meta charset="utf-8">
                  </head>
                  <body>%s
                  </body>
                </html>
                    """, convertSubdocument(markdownDocument));
    }

    // Produces an HTML subdocument without standard HTML boilerplate (e.g. HTML
    // tag, body tag, etc). Assumes {@code markdownSubdocument} is not in the middle
    // of an element.
    String convertSubdocument(String markdownSubdocument) {
        final MarkdownNode markdownTree = parseDocumentTree(markdownSubdocument);
        final StringBuilder htmlBuilder = new StringBuilder();
        markdownTree.toHtml(htmlBuilder);
        return htmlBuilder.toString().strip();
    }

    private MarkdownNode parseDocumentTree(String markdownSubdocument) {
        final MarkdownNode root = MarkdownNode.root();
        parseDocumentTree(markdownSubdocument, root);
        return root;
    }

    private void parseDocumentTree(String markdownSubdocument, MarkdownNode parent) {
        final String[] markdownSections = markdownSubdocument.split("\n\n");
        for (String section : markdownSections) {
            section = section.strip();
            if (section.isBlank()) {
                continue;
            } else if (section.startsWith("######")) {
                // note intentionally including additional "#" within the h6 tag
                parent.addChild(MarkdownNode.simple(H6Element.of(), section.substring(6).strip()));
            } else if (section.startsWith("#####")) {
                parent.addChild(MarkdownNode.simple(H5Element.of(), section.substring(5).strip()));
            } else if (section.startsWith("####")) {
                parent.addChild(MarkdownNode.simple(H4Element.of(), section.substring(4).strip()));
            } else if (section.startsWith("###")) {
                parent.addChild(MarkdownNode.simple(H3Element.of(), section.substring(3).strip()));
            } else if (section.startsWith("##")) {
                parent.addChild(MarkdownNode.simple(H2Element.of(), section.substring(2).strip()));
            } else if (section.startsWith("#")) {
                parent.addChild(MarkdownNode.simple(H1Element.of(), section.substring(1).strip()));
            } else if (section.startsWith("[")) {
                final String linkText = section.substring(1, section.indexOf("]"));
                final String urlText = section.substring(section.indexOf("(") + 1, section.indexOf(")"));
                parent.addChild(MarkdownNode.link(linkText, urlText));
            } else {
                parent.addChild(MarkdownNode.simple(ParagraphElement.of(), section));
            }
        }
    }

    private static class MarkdownNode {
        private MarkdownNode parent;
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
         * @param urlText  href/url to which the link should navigate
         * @return
         */
        public static MarkdownNode link(String linkText, String urlText) {
            final MarkdownNode result = new MarkdownNode(LinkElement.of(urlText));
            result.children.add(new MarkdownNode(NakedElement.of(linkText)));
            return result;
        }

        public void addChild(MarkdownNode child) {
            children.add(child);
            child.parent = this;
        }

        @Nullable
        public MarkdownNode getParent() {
            return parent;
        }

        /**
         * Recursively adds self and children to an output HTML string via (implicit)
         * BFS.
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
}

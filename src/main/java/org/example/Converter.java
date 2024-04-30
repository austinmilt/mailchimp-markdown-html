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
                  <body>
                  %s
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

        // BFS visitation of nodes to properly build the output document
        final List<MarkdownNode> unopenedNodes = new ArrayList<>();
        final List<MarkdownNode> unclosedNodes = new ArrayList<>();
        unopenedNodes.add(markdownTree);
        while (!unopenedNodes.isEmpty()) {
            final MarkdownNode current = unopenedNodes.remove(0);
            if (current.outerElement != null) {
                htmlBuilder.append(current.outerElement.toHtmlOpening());
            }

            unopenedNodes.addAll(current.children);

            // Closing happens in reverse order of opening because nested elements
            // must be closed before their containing elements.
            // TODO is it more efficient to append and then loop backward?
            unclosedNodes.add(0, current);
        }

        for (MarkdownNode current : unclosedNodes) {
            if (current.outerElement != null) {
                htmlBuilder.append(current.outerElement.toHtmlClosing());
            }
        }

        return htmlBuilder.toString();
    }

    MarkdownNode parseDocumentTree(String markdownSubdocument) {
        final MarkdownNode root = new MarkdownNode(null);
        final String[] markdownSections = markdownSubdocument.split("\n\n");
        for (String section : markdownSections) {
            section = section.strip();
            if (section.isBlank()) {
                continue;
            }
            if (section.startsWith("######")) {
                root.children.add(MarkdownNode.simple(H6Element.of(), section.substring(6).strip()));
            } else if (section.startsWith("#####")) {
                root.children.add(MarkdownNode.simple(H5Element.of(), section.substring(5).strip()));
            } else if (section.startsWith("####")) {
                root.children.add(MarkdownNode.simple(H4Element.of(), section.substring(4).strip()));
            } else if (section.startsWith("###")) {
                root.children.add(MarkdownNode.simple(H3Element.of(), section.substring(3).strip()));
            } else if (section.startsWith("##")) {
                root.children.add(MarkdownNode.simple(H2Element.of(), section.substring(2).strip()));
            } else if (section.startsWith("#")) {
                root.children.add(MarkdownNode.simple(H1Element.of(), section.substring(1).strip()));
            } else if (section.startsWith("[")) {
                final String linkText = section.substring(1, section.indexOf("]"));
                final String urlText = section.substring(section.indexOf("(") + 1, section.indexOf(")"));
                root.children.add(MarkdownNode.link(linkText, urlText));
            } else {
                root.children.add(MarkdownNode.simple(ParagraphElement.of(), section));
            }
        }
        return root;
    }

    private static class MarkdownNode {
        public final List<MarkdownNode> children = new ArrayList<>();

        // TODO hook @Nullable up in VS Code
        @Nullable
        public final MarkdownElement outerElement;

        private MarkdownNode(@Nullable MarkdownElement outerElement) {
            this.outerElement = outerElement;
        }

        public static MarkdownNode simple(MarkdownElement element, String content) {
            final MarkdownNode result = new MarkdownNode(element);
            result.children.add(new MarkdownNode(NakedElement.of(content)));
            return result;
        }

        public static MarkdownNode link(String linkText, String urlText) {
            final MarkdownNode result = new MarkdownNode(LinkElement.of(urlText));
            result.children.add(new MarkdownNode(NakedElement.of(linkText)));
            return result;
        }
    }
}

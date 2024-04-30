package org.example;

import org.example.elements.H1Element;
import org.example.elements.H2Element;
import org.example.elements.H3Element;
import org.example.elements.H4Element;
import org.example.elements.H5Element;
import org.example.elements.H6Element;
import org.example.elements.ParagraphElement;

/**
 * Primary utilty class for converting a markdown document string to an HTML string.
 */
class Converter {

    /**
     * Converts a complete markdown document string to a complete HTML document string.
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
}

package org.example;

import java.util.List;

import org.example.elements.MarkdownElement;

public class Converter {

    /**
     * Converts a complete markdown document string to a complete HTML document
     * string.
     * 
     * @param markdownDocument markdown document to be converted
     * @return converted HTML document
     */
    public String convertDocument(String markdownDocument) {
        throw new UnsupportedOperationException();
    }

    // Produces an HTML subdocument without standard HTML boilerplate (e.g. HTML
    // tag, body tag, etc). Assumes {@code markdownSubdocument} is not in the middle
    // of an element.
    String convertSubdocument(String markdownSubdocument) {
        throw new UnsupportedOperationException();
    }

    List<MarkdownElement> parseDocumentTree(String markdownSubdocument) {
        throw new UnsupportedOperationException();
    }
}

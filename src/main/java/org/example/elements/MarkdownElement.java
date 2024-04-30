package org.example.elements;

import java.util.List;

public interface MarkdownElement {
    List<MarkdownElement> getChildren();

    String toHtmlOpening();

    String toHtmlClosing();
}

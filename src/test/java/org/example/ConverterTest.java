package org.example;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConverterTest {

    private Converter converter;

    @BeforeEach
    void setUp() {
        converter = new Converter();
    }

    @Test
    void convert_emptyString_returnsMinimalHTML() {
        final String markdown = "";

        final String html = converter.convertDocument(markdown);

        // TODO this is a fragile test since it embeds some structure in the HTML that
        // isnt necessary to the nature of the test (i.e. that we get valid HTML), so
        // it will fail e.g. when we indent with four spaces instead of two.
        final String expected = """
                <!DOCTYPE html>
                <html lang="en">
                  <head>
                    <meta charset="utf-8">
                  </head>
                  <body>
                  </body>
                </html>
                    """;
        assertEquals(expected, html);
    }

    @Test
    void convertSubdocument_blankString_returnsEmptyString() {
        assertEquals("", converter.convertSubdocument(""));
        assertEquals("", converter.convertSubdocument(" "));
        assertEquals("", converter.convertSubdocument("   "));
        assertEquals("", converter.convertSubdocument("\t\t"));
        assertEquals("", converter.convertSubdocument("\n\r"));
    }

    @Test
    void convertSubdocument_h1_returnsH1HTML() {
        final String markdown = "# Heading 1";

        final String html = converter.convertSubdocument(markdown);

        final String expected = "<h1>Heading 1</h1>";
        assertEquals(expected, html);
    }

    @Test
    void convertSubdocument_h2_returnsH2HTML() {
        final String markdown = "## Heading 2";

        final String html = converter.convertSubdocument(markdown);

        final String expected = "<h2>Heading 2</h2>";
        assertEquals(expected, html);
    }

    @Test
    void convertSubdocument_h3_returnsH3HTML() {
        final String markdown = "### Heading 3";

        final String html = converter.convertSubdocument(markdown);

        final String expected = "<h3>Heading 3</h3>";
        assertEquals(expected, html);
    }

    @Test
    void convertSubdocument_h4_returnsH4HTML() {
        final String markdown = "#### Heading 4";

        final String html = converter.convertSubdocument(markdown);

        final String expected = "<h4>Heading 4</h4>";
        assertEquals(expected, html);
    }

    @Test
    void convertSubdocument_h5_returnsH5HTML() {
        final String markdown = "##### Heading 5";

        final String html = converter.convertSubdocument(markdown);

        final String expected = "<h5>Heading 5</h5>";
        assertEquals(expected, html);
    }

    @Test
    void convertSubdocument_h6_returnsH6HTML() {
        final String markdown = "###### Heading 6";

        final String html = converter.convertSubdocument(markdown);

        final String expected = "<h6>Heading 6</h6>";
        assertEquals(expected, html);
    }

    @Test
    void convertSubdocument_unformattedText_returnsParagraphHTML() {
        final String markdown = "Unformatted text";

        final String html = converter.convertSubdocument(markdown);

        final String expected = "<p>Unformatted text</p>";
        assertEquals(expected, html);
    }

    @Test
    void convertSubdocument_link_returnsATagHTML() {
        final String markdown = "[Link text](https://www.example.com)";

        final String html = converter.convertSubdocument(markdown);

        final String expected = "<a href=\"https://www.example.com\">Link text</a>";
        assertEquals(expected, html);
    }

    @Test
    void convertSubdocument_containsCharactersThatAreNotALink_doesntCreateATag() {
        assertEquals("<p>[Link text https://www.example.com)</p>",
                converter.convertSubdocument("[Link text https://www.example.com)"));

        assertEquals("<p>[Link text https://www.example.com)]</p>",
                converter.convertSubdocument("[Link text https://www.example.com)]"));

        assertEquals("<p>(Link text https://www.example.com)</p>",
                converter.convertSubdocument("(Link text https://www.example.com)"));

        assertEquals("<p>[Link[] text() https://www.example.com)</p>",
                converter.convertSubdocument("[Link[] text() https://www.example.com)"));
    }

    // TODO this is a fragile test since it embeds some structure in the HTML that
    // isnt necessary to the nature of the test (i.e. that we get valid HTML), so
    // it will fail e.g. when we indent with four spaces instead of two.
    @Test
    void convertSubdocument_sampleA_returnsExpectedOutput() {
        final String markdown = """
                # Sample Document

                Hello!

                This is sample markdown for the [Mailchimp](https://www.mailchimp.com) homework assignment.
                    """;
        ;

        final String html = converter.convertSubdocument(markdown);

        final String expected = """
                <h1>Sample Document</h1>

                <p>Hello!</p>

                <p>This is sample markdown for the <a href="https://www.mailchimp.com">Mailchimp</a> homework assignment.</p>
                    """;
        assertEquals(expected, html);
    }

    // TODO this is a fragile test since it embeds some structure in the HTML that
    // isnt necessary to the nature of the test (i.e. that we get valid HTML), so
    // it will fail e.g. when we indent with four spaces instead of two.
    @Test
    void convertSubdocument_sampleB_returnsExpectedOutput() {
        final String markdown = """
                # Header one

                Hello there

                How are you?
                What's going on?

                ## Another Header

                This is a paragraph [with an inline link](http://google.com). Neat, eh?

                ## This is a header [with a link](http://yahoo.com)
                        """;
        ;

        final String html = converter.convertSubdocument(markdown);

        final String expected = """
                <h1>Header one</h1>

                <p>Hello there</p>

                <p>How are you?
                What's going on?</p>

                <h2>Another Header</h2>

                <p>This is a paragraph <a href="http://google.com">with an inline link</a>. Neat, eh?</p>

                <h2>This is a header <a href="http://yahoo.com">with a link</a></h2>
                        """;
        assertEquals(expected, html);
    }

    @Test
    void convertSubdocument_multipleInlineHeadings_ignoresNonFirstHeadings() {
        assertEquals("<h1>Header one # Header two</h1>", converter.convertSubdocument("# Header one # Header two"));
        assertEquals("<h2>Header one ## Header two</h2>", converter.convertSubdocument("## Header one ## Header two"));
        assertEquals("<h3>Header one ### Header two</h3>",
                converter.convertSubdocument("### Header one ### Header two"));
        assertEquals("<h4>Header one #### Header two</h4>",
                converter.convertSubdocument("#### Header one #### Header two"));
        assertEquals("<h5>Header one ##### Header two</h5>",
                converter.convertSubdocument("##### Header one ##### Header two"));
        assertEquals("<h6>Header one ###### Header two</h6>",
                converter.convertSubdocument("###### Header one ###### Header two"));
    }
}

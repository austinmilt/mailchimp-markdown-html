# Markdown-to-HTML

CLI to convert markdown files to HTML.

This code was created as part of a takehome exercise for my Mailchimp interviews
April and May 2024. It is incomplete, inefficient, and generally should not be
considered useful beyond showing how I set up and step through programming exercises.
To that end, some highlights
- [Converter.java](src/main/java/org/example/Converter.java) contains most of the conversion logic.
    It takes a very rough first-pass as parsing an HTML document using string splitting (see `Converter::parseDocumentTree`)
- [ConverterTest.java](src/test/java/org/example/ConverterTest.java) contains primary logic tests, including
    some tests I was not able to make the code pass before I ran out of time. In particular, it doesnt
    handle nested elements like inline links.
- [MarkdownElement.java and implementations](src/main/java/org/example/elements/) contains some "primitives" to
    facilitate conversions to HTML. My intention was to make it easy to add markdown features with
    arbitrary complexity, like tables.
- [Main.java and ConvertMarkdownCommand.java](src/main/java/org/example/) are the main entrypoints to the program,
    but there's not much interesting happening in there.

## Some TODOs I thought of but did not get to
- Stream conversion, including
    - reading the input file as a stream
    - parallel-processing file chunks and combine at the end

- Parse by unicode graphemes rather than simple Java characters

- Enable nested markdown documents, such as a file-based structure with "imports" to break up long documents
    and keep things organized neatly, including creating multiple HTML pages.

- Use randomly-generated test data in addition to known edge cases and basic functionality.

- Add a CLI arg to dictate HTML formatting, especially line-breaks.

- Nice packaging of CLI with picocli (most of the way there)

## Contribute

### Get Requirements
- GraalVM distribution of Java (easy to install with https://sdkman.io/)

### How to use

Run tests & build an executable JAR:

```
$ ./mvnw package
```

Run tests as native image & build a native executable:

```
$ ./mvnw package -Pnative
```

Run application through Maven

```
$ ./mvnw -Dexec.args=--help
```

```
$ ./mvnw -Dexec.args="convert README.md output.html""
```

## Attribution
This repo uses the following picocli template: https://github.com/maciejwalkowiak/java-cli-project-template

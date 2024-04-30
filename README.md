# Markdown-to-HTML

CLI to convert markdown files to HTML.

## TODO
- parallel process large files
- imports (subdocuments)
- invalid markdown errors
- randomly generated test data
- line breakpoint cli arg (e.g. break after 80 characters)

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
$ ./mvnw -Dexec.args="list ."
```

## Attribution
This repo uses the following picocli template: https://github.com/maciejwalkowiak/java-cli-project-template

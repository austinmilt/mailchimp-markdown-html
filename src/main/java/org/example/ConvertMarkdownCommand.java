package org.example;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(name = "convert")
public class ConvertMarkdownCommand implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "file path of the input markdown file")
    private Path input;

    @CommandLine.Parameters(index = "1", description = "file path of the output HTML file")
    private Path output;

    @Override
    public Integer call() {
        try (Scanner reader = new Scanner(new FileReader(input.toFile()))) {
            // TODO stream content through conversion (inc. parallelized)
            final StringBuilder inputString = new StringBuilder();
            while (reader.hasNext()) {
                inputString.append(reader.next());
            }

            final Converter converter = new Converter();
            final String outputString = converter.convertDocument(inputString.toString());

            try (PrintWriter writer = new PrintWriter(output.toFile(), StandardCharsets.UTF_8)) {
                writer.write(outputString);
            }
            return 0;
        } catch (IOException e) {
            System.err.println("Unable to read input file " + e.getMessage());
            return 1;
        }
    }
}

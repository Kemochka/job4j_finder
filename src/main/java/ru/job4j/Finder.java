package ru.job4j;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Finder {
    public static void main(String[] args) {
        ArgsParser parser = ArgsParser.of(args);
        Path startingDir = Paths.get(parser.get("d"));
        Predicate<Path> predicate = new ConditionFinder().getPredicate(parser.get("n"), parser.get("t"));
        try {
            List<Path> foundFiles = findFiles(startingDir, predicate);
            if (parser.get("o") != null && !parser.get("o").isEmpty()) {
                writePathsToFile(foundFiles, parser.get("o"));
            } else {
                for (Path file : foundFiles) {
                    System.out.println(file.toAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Path> findFiles(Path directory, Predicate<Path> predicate) throws IOException {
        List<Path> foundFiles = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(directory)) {
            stream.filter(predicate)
                    .forEach(foundFiles::add);
        }
        return foundFiles;
    }

    private static void writePathsToFile(List<Path> paths, String outputFileName) throws IOException {
        List<String> pathStrings = new ArrayList<>();
        for (Path path : paths) {
            pathStrings.add(path.toAbsolutePath().toString());
        }
        Files.write(Paths.get(outputFileName), pathStrings);
    }
}

package ru.job4j;

import java.util.HashMap;
import java.util.Map;

public class ArgsParser {
    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("Key '" + key + "' is missing");
        }
        return values.get(key);
    }

    private void parse(String[] args) throws IllegalArgumentException {
        for (String arg : args) {
            String[] parts = arg.replaceFirst("-", "").split("=");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid argument format '-key=value'");
            }
            if (parts[1].isEmpty()) {
                throw new IllegalArgumentException("Error: This argument '" + arg + "' does not contain a value");
            }
            if (parts[0].isEmpty()) {
                throw new IllegalArgumentException("Error: This argument '" + arg + "' does not contain a key");
            }
            values.put(parts[0].toLowerCase(), parts[1]);
        }
        if (values.size() != 4) {
            throw new IllegalArgumentException("Not enough arguments. Check the format -d= -n= -t= -o=");
        }
    }

    public static ArgsParser of(String[] args) {
        if (args.length == 0) {
            System.out.println("-d=directory -n=name, mask or regex -t=search type -o=result file");
        }
        ArgsParser names = new ArgsParser();
        names.parse(args);
        return names;
    }
}

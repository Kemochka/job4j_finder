package ru.job4j;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionFinder {
    public static final String NAME_TYPE = "name";
    public static final String MASK_TYPE = "mask";
    public static final String REGEX_TYPE = "regex";

    public Predicate<Path> getPredicate(String name, String type) {
        return switch (type) {
            case NAME_TYPE -> n -> n.toFile().getName().equals(name);
            case REGEX_TYPE -> p -> {
                Pattern pattern = Pattern.compile(name);
                Matcher matcher = pattern.matcher(p.toFile().getName());
                return matcher.find();
            };
            case MASK_TYPE -> getMask(name);
            default -> throw new IllegalArgumentException("Incorrect search");
        };
    }

    private Predicate<Path> getMask(String name) {
        int i = name.indexOf("*");
        if ((i == -1) || i != name.lastIndexOf("*")) {
            throw new IllegalArgumentException("Ошибка маски");
        }
        String left = name.substring(0, i);
        String right = name.substring(i + 1);
        if (i == 0) {
            return path -> path.toFile().getName().endsWith(right);
        }
        if (i == name.length() - 1) {
            return path -> path.toFile().getName().startsWith(left);
        }
        return path -> path.toFile().getName().startsWith(left) && path
                .toFile()
                .getName()
                .endsWith(right);
    }
}

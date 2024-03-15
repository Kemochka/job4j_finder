package ru.job4j;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArgsParserTest {
    @Test
    public void whenNotException() {
        ArgsParser ap = ArgsParser.of(
                new String[]{"-d=/homeDir/", "-n=*.xml", "-t=mask", "-o=log.txt"});
        String expected = ap.get("n");
        assertEquals(expected, "*.xml");
    }

    @Test
    public void whenLowerCase() {
        ArgsParser ap = ArgsParser.of(new String[]{"-d=/homeDir/", "-n=*.xml",
                "-T=mask", "-o=log.txt"});
        String excepted = ap.get("t");
        assertEquals(excepted, "mask");
    }

    @Test
    public void whenInvalidArgFormat() {
        assertThatThrownBy(() -> ArgsParser.of(new String[]{"-d=", "-n=*.xml", "-t=mask", "-o=log.txt"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid argument format '-key=value'");


    }

    @Test
    public void whenTheKeyNotFound() {
        assertThatThrownBy(() -> ArgsParser.of(new String[]{"-d=/homeDir/", "=*.xml", "-t=mask", "-o=log.txt"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Error: This argument '=*.xml' does not contain a key");
    }

    @Test
    public void whenInvalidF() {
        assertThatThrownBy(() -> ArgsParser.of(new String[]{"-d=/homeDir/", "-n=*.xml=m", "-t=mask", "-o=log.txt"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid argument format '-key=value'");
    }

    @Test
    public void whenArgNotFound() {
        assertThatThrownBy(() -> ArgsParser.of(new String[]{}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough arguments. Check the format -d= -n= -t= -o=");
    }
}
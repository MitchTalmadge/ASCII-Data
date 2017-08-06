package com.mitchtalmadge.asciigraph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestUtils {

    /**
     * Reads a test file to a String.
     *
     * @param filePath The path to the file, relative to the test resources directory.
     * @return The String contents of the file.
     * @throws IOException If the file could not be found or read.
     */
    public static String readFileToString(String filePath) throws IOException {
        File file = new File("src/test/resources/" + filePath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        return new String(fileBytes);
    }

    /**
     * Replaces all CRLF endings with LF endings.
     * @param content The String content.
     * @return The String content with modified line endings.
     */
    public static String commonizeLineEndings(String content) {
        return content.replaceAll("\r", "\n").replaceAll("\n\n", "\n");
    }

}

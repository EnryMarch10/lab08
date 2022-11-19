package it.unibo.mvc.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ConfigurationReader {

    private static final String separator = ":";
    private final File file;
    private final String[] names;

    /**
     * @param file the file that will be read.
     * @param file the "key" format expected.
     */
    public ConfigurationReader(File file, String[] names) {
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException();
        }
        this.names = Arrays.copyOf(Objects.requireNonNull(names), names.length);
        this.file = file;
    }
    
    /**
     * Reads informations specified.
     * @return a {@link List} of {@link String} of the informations read.
     */
    public List<String> readInformations() {
        List<String> result = new LinkedList<>();

        try (BufferedReader r = new BufferedReader(new FileReader(file.getAbsolutePath(), StandardCharsets.UTF_8))) {
            String line = r.readLine();
            for(int i=0; line != null; i++) {
                String[] lineItems = line.split(separator);
                if ((lineItems.length != 2) || (!lineItems[0].equals(names[i]))) {
                    throw new IllegalFileFormatException();
                }
                result.add(lineItems[1].trim());
                line = r.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}

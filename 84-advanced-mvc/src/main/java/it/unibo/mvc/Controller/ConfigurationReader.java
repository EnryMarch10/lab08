package it.unibo.mvc.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class ConfigurationReader {

    private static final String separator = ":";
    private final File file;
    //private final String[] names;
    private final HashSet<String> keys;

    /**
     * @param file the file that will be read.
     * @param file the "key" format expected.
     */
    public ConfigurationReader(File file, Collection<String> names) {
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException();
        }
        this.keys = new LinkedHashSet<>(names);
        this.file = file;
    }
    
    /**
     * Reads informations specified.
     * @return a {@link List} of {@link String} of the informations read.
     */
    public HashMap<String, String> readInformations() {
        HashMap<String, String> result = new LinkedHashMap<>();

        try (BufferedReader r = new BufferedReader(new FileReader(file.getAbsolutePath(), StandardCharsets.UTF_8))) {
            int i=0;
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                if (!line.trim().equals("")) {
                    String[] lineItems = line.split(separator);
                    String key = lineItems[0].trim();
                    if ((lineItems.length != 2) || (!keys.contains(key))) {
                        throw new IllegalFileFormatException();
                    }
                    result.put(key, lineItems[1].trim());
                    i++;
                }
            }
            if (keys.size() != i) {
                throw new IllegalFileFormatException();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}

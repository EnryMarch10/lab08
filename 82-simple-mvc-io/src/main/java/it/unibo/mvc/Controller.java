package it.unibo.mvc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Application controller. Performs the I/O.
 */
public class Controller {
    private File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "output.txt");

    public void setFile(final File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public String getFilePath() {
        return this.file.getAbsolutePath();
    }

    public boolean writeContent(final String buffer) {
        boolean result = true;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error in creating file"); // NOPMD: allowed as this is just an exercise
                e.printStackTrace(); // NOPMD: allowed as this is just an exercise
                result = false;
            }
        }

        try (BufferedWriter r = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            r.write(buffer);
        } catch (IOException e) {
            System.out.println("Error in writing file"); // NOPMD: allowed as this is just an exercise
            e.printStackTrace(); // NOPMD: allowed as this is just an exercise
            result = false;
        }

        return result;
    }
}

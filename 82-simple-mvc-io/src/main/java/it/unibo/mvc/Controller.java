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

    private static String defaultFileName = "output.txt";
    private File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + defaultFileName);

    /**
     * This method changes the currently selected {@link File}.
     * If not setted the {@link File} is the one specified
     * by the field {@link defaultFileName}.
     * @param file
     */
    public final void setFile(final File file) {
        this.file = file;
    }

    /**
     * @return this value could be the one 
     * setted in {@link setFile}or the
     * default one.
     */
    public final File getFile() {
        return this.file;
    }

    /**
     * @return the path associated to the currently selected file.
     * N.B. path could not exist.
     */
    public final String getFilePath() {
        return this.file.getAbsolutePath();
    }

    /**
     * This method write a {@link String} in the currently selected file.
     * @param buffer the buffer to be written.
     * @return true indicates success, false error in writing.
     */
    public final boolean writeContent(final String buffer) {
        boolean result = false;
        try {
            result = file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error in creating file"); // NOPMD: allowed as this is just an exercise
            e.printStackTrace(); // NOPMD: allowed as this is just an exercise
        }

        try (BufferedWriter r = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            r.write(buffer);
            result = true;
        } catch (IOException e) {
            System.out.println("Error in writing file"); // NOPMD: allowed as this is just an exercise
            e.printStackTrace(); // NOPMD: allowed as this is just an exercise
        }

        return result;
    }
}

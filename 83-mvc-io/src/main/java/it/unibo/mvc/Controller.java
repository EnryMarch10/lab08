package it.unibo.mvc;

import java.util.List;

/**
 * Interface used.
 */
public interface Controller {

    /**
     * Adds the {@link String} that will be ready for the {@link print} action.
     * @param output the String to set
     */
    void setString(String output);
    /**
     * @return The last {@link String} added.
     */
    String getString();
    /**
     * @return The {@link String} elements added.
     */
    List<String> getHistory();
    /**
     * prints the last {@link String} added in stdout.
     */
    void print();
}

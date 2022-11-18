package it.unibo.mvc;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * An implementation of Controller.
 */
public final class SimpleController implements Controller {

    private final Deque<String> outputs;

    /**
     * Creates an istance of this class.
     */
    public SimpleController() {
        outputs = new LinkedList<>();
    }
    @Override
    public void setString(final String output) {
        outputs.add(Objects.requireNonNull(output));
    }
    @Override
    public String getString() {
        return outputs.getLast();
    }
    @Override
    public List<String> getHistory() {
        return List.copyOf(outputs);
    }
    @Override
    public void print() {
        if (!outputs.isEmpty()) {
            System.out.println(getString()); // NOPMD: allowed as this is just an exercise
        } else {
            throw new IllegalStateException();
        }
    }
}

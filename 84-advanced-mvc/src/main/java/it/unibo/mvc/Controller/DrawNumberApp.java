package it.unibo.mvc.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import it.unibo.mvc.Model.Configuration;
import it.unibo.mvc.Model.DrawNumber;
import it.unibo.mvc.Model.DrawNumberImpl;
import it.unibo.mvc.Model.DrawResult;
import it.unibo.mvc.View.DrawNumberView;
import it.unibo.mvc.View.DrawNumberViewImpl;
import it.unibo.mvc.View.PrintStreamView;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private static final String min = "minimum";
    private static final String max = "maximum";
    private static final String attempts = "attempts";

    private static final int MIN;
    private static final int MAX;
    private static final int ATTEMPTS;

    static {
        List<String> list = new ArrayList<>();
        list.add(min);
        list.add(max);
        list.add(attempts);
        ConfigurationReader reader = new ConfigurationReader(new File(ClassLoader.getSystemResource("config.yml").getPath()), list);
        HashMap<String, String> map = reader.readInformations();
        MIN = Integer.parseInt(map.get(min));
        MAX = Integer.parseInt(map.get(max));
        ATTEMPTS = Integer.parseInt(map.get(min));
    }

    private final DrawNumber model;
    private final List<DrawNumberView> views;

    /**
     * @param views
     *            the views to attach
     */
    public DrawNumberApp(final DrawNumberView... views) {
        /*
         * Side-effect proof
         */
        this.views = Arrays.asList(Arrays.copyOf(views, views.length));
        for (final DrawNumberView view: views) {
            view.setObserver(this);
            view.start();
        }
        this.model = new DrawNumberImpl(new Configuration.Builder().setMin(MIN).setMax(MAX).setAttempts(ATTEMPTS).build());
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view: views) {
                view.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView view: views) {
                view.numberIncorrect();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        /*
         * A bit harsh. A good application should configure the graphics to exit by
         * natural termination when closing is hit. To do things more cleanly, attention
         * should be paid to alive threads, as the application would continue to persist
         * until the last thread terminates.
         */
        System.exit(0);
    }

    /**
     * @param args
     *            ignored
     * @throws FileNotFoundException 
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp(
            new DrawNumberViewImpl(),
            new DrawNumberViewImpl(),
            new PrintStreamView(System.out)
        );
    }

}

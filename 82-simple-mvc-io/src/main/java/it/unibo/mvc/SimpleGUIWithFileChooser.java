package it.unibo.mvc;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    private final JFrame frame = new JFrame();
    private static final int PROPORTION = 5;
    private final Controller controller = new Controller();

    /**
     * Creates a new BadIOGUI.
     */
    public SimpleGUIWithFileChooser() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel canvas1 = new JPanel(new BorderLayout());
        final JButton save = new JButton("SAVE");
        canvas1.add(save, BorderLayout.SOUTH);
        final JTextArea tArea = new JTextArea();
        canvas1.add(tArea, BorderLayout.CENTER);
        frame.setContentPane(canvas1);

        final JPanel canvas2 = new JPanel(new BorderLayout());
        final JTextField tField = new JTextField(controller.getFilePath());
        tField.setEditable(false);
        final JButton browse = new JButton("BROWSE");
        canvas2.add(tField, BorderLayout.CENTER);
        canvas2.add(browse, BorderLayout.SOUTH);
        canvas1.add(canvas2, BorderLayout.NORTH);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                if (controller.writeContent(tArea.getText())) {
                    JOptionPane.showMessageDialog(frame, "Writing OK", "file writer", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "ERROR in writing", "file writer", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                final JFileChooser fileChooser = new JFileChooser(tField.getText());
                final int result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    controller.setFile(fileChooser.getSelectedFile());
                    tField.setText(controller.getFilePath());
                } else if (result == JFileChooser.ERROR_OPTION) {
                    JOptionPane.showMessageDialog(frame, "ERROR in setting", "file setter", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void display() {
        /*
         * Make the frame one fifth the resolution of the screen. This very method is
         * enough for a single screen setup. In case of multiple monitors, the
         * primary is selected. In order to deal coherently with multimonitor
         * setups, other facilities exist (see the Java documentation about this
         * issue). It is MUCH better than manually specify the size of a window
         * in pixel: it takes into account the current resolution.
         */
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / PROPORTION, sh / PROPORTION);
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        frame.setLocationByPlatform(true);
        /*
         * OK, ready to push the frame onscreen
         */
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Launches the application.
     *
     * @param args ignored
     */
    public static void main(final String... args) {
       new SimpleGUIWithFileChooser().display();
    }

}

import javax.swing.*;
import java.awt.*;

public class menu {

    private JFrame frame;
    private JLabel title;
    private JButton newobsession;
    private JButton setup;
    private JButton fetchallrecords;

    public menu() {
        frame = new JFrame("Obsession Tracker");

        // Create and configure the components
        title = new JLabel("Obsession Tracker");
        newobsession = new JButton("New Obsession");
        setup = new JButton("Setup");
        fetchallrecords = new JButton("Fetch All Records");

        // Add the components to the frame
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(Box.createVerticalGlue());
        frame.add(title);
        frame.add(Box.createVerticalStrut(10));
        frame.add(newobsession);
        frame.add(Box.createVerticalStrut(10));
        frame.add(setup);
        frame.add(Box.createVerticalStrut(10));
        frame.add(fetchallrecords);

        frame.add(Box.createVerticalGlue());



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(200,200);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Menu();
            }
        });
    }
}

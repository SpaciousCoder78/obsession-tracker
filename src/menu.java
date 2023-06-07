//importing modules
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class menu {
    //defining swing items
    private JFrame frame;
    private JLabel title;
    private JPanel panel;
    private JButton newobsession;
    private JButton setup;
    private JButton fetchallrecords;

    //function for setting up the database
    public static void setitup() {
        Connection conn = null;

        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Address of the database
            String url = "jdbc:sqlite:obsessiondata.db";
            // Connecting to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection Established");

            // Create a statement
            Statement statement = conn.createStatement();

            // SQL statement to create a table
            String createtable = "CREATE TABLE IF NOT EXISTS tracker (id INTEGER PRIMARY KEY, " +
                    "DATE VARCHAR,TIME VARCHAR, OBSESSION VARCHAR)";



            // Execute the SQL statement
            statement.executeUpdate(createtable);

            System.out.println("Table created successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public menu() {
        frame = new JFrame("Obsession Tracker");

        // Create and configure the components
        panel= new JPanel();
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

        //action listeners for buttons

        //action listener for new obsessions
        ActionListener newob = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newentry newentry = new newentry();
            }
        };
        newobsession.addActionListener(newob);
        //action listener for setup
        ActionListener setmax = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setitup();
                JOptionPane.showMessageDialog(null,"Setup Complete");
            }
        };

        setup.addActionListener(setmax);

        //action listener for display records

        ActionListener fetxch = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayrecords displayrecords = new displayrecords();
            }
        };

        fetchallrecords.addActionListener(fetxch);



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(300,300);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new menu();
            }
        });
    }
}


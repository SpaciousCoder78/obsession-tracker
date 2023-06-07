import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class displayrecords {
    private JTextArea textArea1;
    private JPanel panel1;

    private JFrame frame;

    public displayrecords(){
        frame = new JFrame("Existing Records");
        textArea1 = new JTextArea(45, 150);

        // Set the layout manager for the frame and add the panel to it
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(textArea1), BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
        frame.setSize(600, 600);

        // Fetch and display the records
        fetchAndDisplayRecords();
    }

    private void fetchAndDisplayRecords() {
        String url = "jdbc:sqlite:obsessiondata.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tracker")) {

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                int id = rs.getInt("id");
                String DATE = rs.getString("DATE");
                String TIME = rs.getString("TIME");
                String OBSESSION = rs.getString("OBSESSION");

                sb.append("ID: ").append(id).append("\n");
                sb.append("Date: ").append(DATE).append("\n");
                sb.append("Time: ").append(TIME).append("\n");
                sb.append("Obsession: ").append(OBSESSION).append("\n\n");
            }

            textArea1.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

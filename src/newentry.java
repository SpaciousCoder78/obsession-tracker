import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class newentry {

    //defining frame items
    private JFrame frame;
    private JPanel panel1;
    private JLabel label1;

    private JLabel label3;
    private JTextField textField2;
    private JLabel label4;
    private JTextField textField3;
    private JLabel label5;
    private JTextArea textArea1;

    private JButton submit;

    private void insertDataToTable(String DATE, String TIME, String OBSESSION) {
        String url = "jdbc:sqlite:obsessiondata.db";  // Replace with the actual path to your SQLite database
        String selectMaxIdSql = "SELECT MAX(id) FROM tracker";
        String insertSql = "INSERT INTO tracker (id, DATE, TIME, OBSESSION) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement selectMaxIdStmt = conn.prepareStatement(selectMaxIdSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            // Get the maximum id from the table
            int maxId = 0;
            try (ResultSet rs = selectMaxIdStmt.executeQuery()) {
                if (rs.next()) {
                    maxId = rs.getInt(1);
                }
            }

            // Increment the id value by 1
            int id = maxId + 1;

            // Insert the data into the table
            insertStmt.setInt(1, id);
            insertStmt.setString(2, DATE);
            insertStmt.setString(3, TIME);
            insertStmt.setString(4, OBSESSION);
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(null,"Record Saved");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public newentry(){
        //tagging frame items
         frame = new JFrame("New Entry");
         panel1= new JPanel();
         label1= new JLabel("New Entry");
         label3= new JLabel("Date: ");
         textField2= new JTextField();
         label4= new JLabel("Time: ");
         textField3=new JTextField();
         label5= new JLabel("Obsession: ");
         textArea1 = new JTextArea(2,20);
         submit= new JButton("Submit");


         //adding items to frame
        frame.add(panel1);
        frame.add(label1);
        frame.add(label3);
        frame.add(textField2);
        frame.add(label4);
        frame.add(textField3);
        frame.add(label5);
        frame.add(textArea1);
        frame.add(submit);

        GroupLayout layout = new GroupLayout(panel1);
        panel1.setLayout(layout);

        // Configure the horizontal grouping
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(label1)
                        .addComponent(label3)
                        .addComponent(label4)
                        .addComponent(label5))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(textField2)
                        .addComponent(textField3)
                        .addComponent(textArea1)
                        .addComponent(submit)));

        // Configure the vertical grouping
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(label1)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label3)
                        .addComponent(textField2))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label4)
                        .addComponent(textField3))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label5)
                        .addComponent(textArea1))
                .addComponent(submit));

        // Set the layout manager for the frame and add the panel to it
        frame.setLayout(new BorderLayout());
        frame.add(panel1, BorderLayout.CENTER);

         frame.pack();
         frame.setVisible(true);
         frame.setSize(600,600);


         //action listener for submit button
        ActionListener subm = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the text from the text fields and text area
                String DATE = textField2.getText();
                String TIME = textField3.getText();
                String OBSESSION = textArea1.getText();

                // Insert the data into the SQLite table
                insertDataToTable( DATE,TIME , OBSESSION);

                // Clear the text fields and text area

                textField2.setText("");
                textField3.setText("");
                textArea1.setText("");
            }
        };
        submit.addActionListener(subm);


    }
}


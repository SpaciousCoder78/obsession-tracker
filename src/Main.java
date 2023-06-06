//importing modules
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.lang.System;

public class Main {

    public static void main(String[]args) {
        menu();
    }
    public static void menu() {
        int x = 1;
        while (x == 1) {
            System.out.println("------------------------Obsession Tracker------------------------");
            System.out.println("----------------------------Menu----------------------------------");
                System.out.println("1.Setup the application");
                System.out.println("2.Enter a new obsession");
                System.out.println("3.Fetch all obsession records");
                System.out.println("4.Exit");
                System.out.println("Enter the option: ");
                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();

                if (option == 1) {
                    setup();
                } else if (option == 2) {
                    newentry();
                } else if (option == 3) {
                    fetchrecords();
                } else if (option == 4) {
                    break;
                } else {
                    System.out.println("Invalid option. Please try again.");
            }
        }
        System.exit(0); // Exit the program
    }



    //function for creating a new entry
    public static void newentry(){
        Scanner scan = new Scanner(System.in);
        //obsession id
        System.out.println("Enter the Obsession ID: ");
        int id = scan.nextInt();
        scan.nextLine();
        //date
        System.out.println("Enter the date: ");
        String DATE= scan.nextLine();
        //time
        System.out.println("Enter the time: ");
        String TIME= scan.nextLine();
        //thought
        System.out.println("Enter the intrusive thought or obsession: ");
        String OBSESSION = scan.nextLine();

        // Establishing a database connection
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:obsessiondata.db")) {
            String query = "INSERT INTO tracker (id,DATE,TIME,OBSESSION) VALUES (?,?,?,?)";

            // Creating a PreparedStatement
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.setString(2,DATE);
                stmt.setString(3,TIME);
                stmt.setString(4,OBSESSION);

                // Executing the query
                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("New obsession record inserted successfully.");
                } else {
                    System.out.println("Failed to insert new obsession record.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing the query: " + e.getMessage());
        }
         menu();
    }

    public static void fetchrecords(){
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:obsessiondata.db")) {
            String query = "SELECT * FROM tracker";

            // Creating a Statement
            try (Statement stmt = conn.createStatement()) {
                // Executing the query
                ResultSet rs = stmt.executeQuery(query);

                // Iterating over the result set
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String date = rs.getString("DATE");
                    String time = rs.getString("TIME");
                    String thought = rs.getString("OBSESSION");

                    System.out.println("-------Results------");
                    System.out.println("ID: " + id);
                    System.out.println("Date: " + date);
                    System.out.println("Time: " + time);
                    System.out.println("Thought: " + thought);
                    System.out.println("--------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing the query: " + e.getMessage());
        }
        menu();
    }
    public static void setup() {
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
        menu();
    }
}


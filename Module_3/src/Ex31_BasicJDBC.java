import java.sql.*;

/**
 * Exercise 31: Basic JDBC Connection
 * Objective: Connect Java with MySQL and retrieve data.
 *
 * Prerequisites:
 *   1. MySQL running on localhost:3306
 *   2. Run Module_2/schema.sql to create community_portal DB
 *   3. Download MySQL Connector/J: https://dev.mysql.com/downloads/connector/j/
 *
 * Compile: javac -cp ".;mysql-connector-j-9.x.x.jar" Ex31_BasicJDBC.java
 * Run:     java  -cp ".;mysql-connector-j-9.x.x.jar" Ex31_BasicJDBC
 */
public class Ex31_BasicJDBC {

    static final String URL  = "jdbc:mysql://localhost:3306/community_portal";
    static final String USER = "root";
    static final String PASS = "";   // update with your MySQL password

    public static void main(String[] args) {
        System.out.println("=== Basic JDBC Connection Demo ===\n");

        // Step 1: Load JDBC driver (auto-loaded in JDBC 4.0+ from JAR)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded.");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found. Add mysql-connector-j.jar to classpath.");
            return;
        }

        // Steps 2-4: Connect, execute query, process results
        String sql = "SELECT user_id, full_name, email, city FROM Users ORDER BY user_id";

        try (Connection  conn = DriverManager.getConnection(URL, USER, PASS);
             Statement   stmt = conn.createStatement();
             ResultSet   rs   = stmt.executeQuery(sql)) {

            System.out.println("Connected to: " + conn.getCatalog());
            System.out.println("\n--- Users Table ---");
            System.out.printf("%-5s %-20s %-30s %-15s%n", "ID", "Name", "Email", "City");
            System.out.println("-".repeat(72));

            while (rs.next()) {
                System.out.printf("%-5d %-20s %-30s %-15s%n",
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("city"));
            }

        } catch (SQLException e) {
            System.out.println("SQL Error [" + e.getSQLState() + "]: " + e.getMessage());
        }
    }
}

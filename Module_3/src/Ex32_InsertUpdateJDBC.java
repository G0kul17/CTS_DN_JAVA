import java.sql.*;

/**
 * Exercise 32: Insert and Update with JDBC
 * Objective: Perform insert/update queries using PreparedStatement.
 *
 * Uses the StudentDAO pattern against community_portal.Users table.
 *
 * Compile: javac -cp ".;mysql-connector-j-9.x.x.jar" Ex32_InsertUpdateJDBC.java
 * Run:     java  -cp ".;mysql-connector-j-9.x.x.jar" Ex32_InsertUpdateJDBC
 */
public class Ex32_InsertUpdateJDBC {

    static final String URL  = "jdbc:mysql://localhost:3306/community_portal";
    static final String USER = "root";
    static final String PASS = "";

    // ── DAO (Data Access Object) ─────────────────────────────
    static class StudentDAO {
        private final Connection conn;
        StudentDAO(Connection conn) { this.conn = conn; }

        /** Insert a new user; returns generated ID */
        int insertUser(String name, String email, String city, String date) throws SQLException {
            String sql = "INSERT INTO Users (full_name, email, city, registration_date) VALUES (?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, name); ps.setString(2, email);
                ps.setString(3, city); ps.setDate(4, java.sql.Date.valueOf(date));
                ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                return keys.next() ? keys.getInt(1) : -1;
            }
        }

        /** Update city for a user by ID; returns rows affected */
        int updateCity(int id, String city) throws SQLException {
            String sql = "UPDATE Users SET city = ? WHERE user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, city); ps.setInt(2, id);
                return ps.executeUpdate();
            }
        }

        void listUsers() throws SQLException {
            try (Statement s = conn.createStatement();
                 ResultSet rs = s.executeQuery("SELECT user_id, full_name, email, city FROM Users ORDER BY user_id")) {
                System.out.printf("%-5s %-20s %-30s %-15s%n", "ID", "Name", "Email", "City");
                System.out.println("-".repeat(72));
                while (rs.next())
                    System.out.printf("%-5d %-20s %-30s %-15s%n",
                        rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== JDBC Insert & Update Demo ===\n");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            StudentDAO dao = new StudentDAO(conn);

            System.out.println("--- Before Insert ---");
            dao.listUsers();

            int newId = dao.insertUser("Frank Miller", "frank@example.com", "Seattle", "2025-06-01");
            System.out.println("\n✔ Inserted user ID: " + newId);

            int rows = dao.updateCity(newId, "Portland");
            System.out.println("✔ Updated city to Portland (" + rows + " row)");

            System.out.println("\n--- After Insert & Update ---");
            dao.listUsers();

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}

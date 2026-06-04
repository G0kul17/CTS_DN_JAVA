import java.sql.*;

/**
 * Exercise 33: Transaction Handling in JDBC
 * Objective: Use transactions for atomic multi-step operations.
 *
 * Simulates a money transfer: debit sender → credit receiver.
 * Uses setAutoCommit(false), commit(), rollback().
 *
 * Setup SQL (run once in MySQL):
 *   USE community_portal;
 *   CREATE TABLE IF NOT EXISTS accounts (
 *     account_id INT PRIMARY KEY AUTO_INCREMENT,
 *     owner_name VARCHAR(100) NOT NULL,
 *     balance    DECIMAL(12,2) NOT NULL DEFAULT 0.00
 *   );
 *   INSERT INTO accounts (owner_name, balance) VALUES ('Alice',1000.00),('Bob',500.00);
 *
 * Compile: javac -cp ".;mysql-connector-j-9.x.x.jar" Ex33_TransactionHandling.java
 * Run:     java  -cp ".;mysql-connector-j-9.x.x.jar" Ex33_TransactionHandling
 */
public class Ex33_TransactionHandling {

    static final String URL  = "jdbc:mysql://localhost:3306/community_portal";
    static final String USER = "root";
    static final String PASS = "";

    static void transfer(Connection conn, int fromId, int toId, double amount) throws SQLException {
        conn.setAutoCommit(false);   // Begin transaction
        try (
            PreparedStatement debit  = conn.prepareStatement(
                "UPDATE accounts SET balance = balance - ? WHERE account_id = ? AND balance >= ?");
            PreparedStatement credit = conn.prepareStatement(
                "UPDATE accounts SET balance = balance + ? WHERE account_id = ?")
        ) {
            debit.setDouble(1, amount); debit.setInt(2, fromId); debit.setDouble(3, amount);
            if (debit.executeUpdate() == 0)
                throw new SQLException("Debit failed: insufficient funds or invalid ID " + fromId);

            credit.setDouble(1, amount); credit.setInt(2, toId);
            if (credit.executeUpdate() == 0)
                throw new SQLException("Credit failed: invalid account ID " + toId);

            conn.commit();
            System.out.printf("✔ Transferred $%.2f from account %d to account %d%n", amount, fromId, toId);
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("✘ Rolled back! Reason: " + e.getMessage());
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    static void printBalances(Connection conn) throws SQLException {
        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery("SELECT account_id, owner_name, balance FROM accounts")) {
            System.out.printf("%-12s %-15s %s%n", "Account", "Owner", "Balance");
            System.out.println("-".repeat(38));
            while (rs.next())
                System.out.printf("%-12d %-15s $%.2f%n",
                    rs.getInt(1), rs.getString(2), rs.getDouble(3));
        }
    }

    public static void main(String[] args) {
        System.out.println("=== JDBC Transaction Demo ===\n");
        System.out.println("NOTE: Create the accounts table first — see setup SQL in comments.\n");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.println("--- Before Transfer ---");
            printBalances(conn);
            transfer(conn, 1, 2, 250.00);
            System.out.println("\n--- After Transfer ---");
            printBalances(conn);
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private static Scanner scanner = new Scanner(System.in);

    public static void createAccount() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter account type (SAVINGS/CURRENT): ");
        String accountType = scanner.nextLine();

        String sql = "INSERT INTO customers (name, email, phone, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int customerId = rs.getInt(1);
                String accountNumber = "ACC" + customerId;
                String sqlAccount = "INSERT INTO accounts (customer_id, account_number, account_type) VALUES (?, ?, ?)";
                try (PreparedStatement pstmtAccount = conn.prepareStatement(sqlAccount)) {
                    pstmtAccount.setInt(1, customerId);
                    pstmtAccount.setString(2, accountNumber);
                    pstmtAccount.setString(3, accountType);
                    pstmtAccount.executeUpdate();
                }
                System.out.println("Account created successfully! Account Number: " + accountNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkBalance() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        String sql = "SELECT balance FROM accounts WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Balance: " + rs.getDouble("balance"));
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
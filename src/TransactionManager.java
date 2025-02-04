import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TransactionManager {
    private static Scanner scanner = new Scanner(System.in);

    public static void deposit() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();

        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setDouble(1, amount);
            pstmt.setString(2, accountNumber);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Deposit successful.");
                recordTransaction(accountNumber, "DEPOSIT", amount);
            } else {
                System.out.println("Account not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void withdraw() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        String sql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND balance >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, amount);
            pstmt.setString(2, accountNumber);
            pstmt.setDouble(3, amount);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Withdrawal successful.");
                recordTransaction(accountNumber, "WITHDRAWAL", amount);
            } else {
                System.out.println("Insufficient balance or account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void transferFunds() {
        System.out.print("Enter sender account number: ");
        String senderAccount = scanner.nextLine();
        System.out.print("Enter receiver account number: ");
        String receiverAccount = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        String sqlSender = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND balance >= ?";
        String sqlReceiver = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        try {Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmtSender = conn.prepareStatement(sqlSender);
             PreparedStatement pstmtReceiver = conn.prepareStatement(sqlReceiver);

            conn.setAutoCommit(false);

            pstmtSender.setDouble(1, amount);
            pstmtSender.setString(2, senderAccount);
            pstmtSender.setDouble(3, amount);
            int senderRows = pstmtSender.executeUpdate();

            pstmtReceiver.setDouble(1, amount);
            pstmtReceiver.setString(2, receiverAccount);
            int receiverRows = pstmtReceiver.executeUpdate();

            if (senderRows > 0 && receiverRows > 0) {
                conn.commit();
                System.out.println("Transfer successful.");
                recordTransaction(senderAccount, "TRANSFER", -amount);
                recordTransaction(receiverAccount, "TRANSFER", amount);
            } else {
                conn.rollback();
                System.out.println("Transfer failed. Insufficient balance or invalid account.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void recordTransaction(String accountNumber, String type, double amount) {
        String sql = "INSERT INTO transactions (account_id, transaction_type, amount) " +
                "SELECT account_id, ?, ? FROM accounts WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, type);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, accountNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
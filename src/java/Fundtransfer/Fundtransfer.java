package Fundtransfer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Fundtransfer extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webapp1";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
//        HttpSession session = request.getSession();
//        session.setAttribute("loginAttempts", 0);

        HttpSession session = request.getSession(true); // Get the existing session or create a new one if it doesn't exist

        // Check if the session already contains the loginAttempts attribute
        if (session.getAttribute("loginAttempts") == null) {
            // Initialize loginAttempts to 0 only once for a new session
            session.setAttribute("loginAttempts", 0);
        }
        try (PrintWriter out = response.getWriter()) {
            String senderAccountNo = request.getParameter("SenderAccountNo");
            String receiverAccountNo = request.getParameter("ReceiverAccountNo");
            String sendingAmountStr = request.getParameter("SendingAmount");
            String password = request.getParameter("Password");
            try {
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

                // Check the password against the customer table
                String passwordQuery = "SELECT password FROM customers WHERE ID = ?";
                PreparedStatement passwordStatement = conn.prepareStatement(passwordQuery);
                passwordStatement.setString(1, senderAccountNo);
                ResultSet passwordResultSet = passwordStatement.executeQuery();

                if (passwordResultSet.next()) {
                    String storedPassword = passwordResultSet.getString("password");
                    if (!password.equals(storedPassword)) {
                        // Incorrect password, increment login attempts
                        int loginAttempts = (int) session.getAttribute("loginAttempts");
                        loginAttempts++;
                        session.setAttribute("loginAttempts", loginAttempts);
                        if (loginAttempts >= 2) {
                            session.setAttribute("loginAttempts", 0);
                            // Generate a JavaScript script to redirect the entire window
                            String redirectScript = "<script>localStorage.clear(); window.location.href = 'Login.html';</script>";

                            // Set the content type to HTML
                            response.setContentType("text/html");

                            // Write the JavaScript script to the response
                            out.println(redirectScript);
                            return; // Ensure that the servlet stops processing
                        }

                        System.out.println("Login Attempts: " + loginAttempts);
                        out.println("Error: Incorrect password. Attempts left: " + (2 - loginAttempts));
                        return;
                    }
                } else {
                    out.println("Error: Sender account not found.");
                    return;
                }
                //if transaction get succussfel then again set the loginAttempts to 0
                session.setAttribute("loginAttempts", 0);
                // Step 1: Retrieve sender's and receiver's account balances
                String selectSenderQuery = "SELECT balance FROM accounts WHERE ID = ?";
                PreparedStatement senderStatement = conn.prepareStatement(selectSenderQuery);
                senderStatement.setString(1, senderAccountNo);
                ResultSet senderResultSet = senderStatement.executeQuery();

                String selectReceiverQuery = "SELECT balance FROM accounts WHERE ID= ?";
                PreparedStatement receiverStatement = conn.prepareStatement(selectReceiverQuery);
                receiverStatement.setString(1, receiverAccountNo);
                ResultSet receiverResultSet = receiverStatement.executeQuery();

                if (senderResultSet.next() && receiverResultSet.next()) {
                    double senderBalance = senderResultSet.getDouble("balance");
                    double receiverBalance = receiverResultSet.getDouble("balance");

                    // Step 2: Check if the sender has sufficient funds
                    double sendingAmount = Double.parseDouble(sendingAmountStr);
                    if (senderBalance >= sendingAmount) {
                        // Step 3: Update sender's and receiver's balances
                        double newSenderBalance = senderBalance - sendingAmount;
                        double newReceiverBalance = receiverBalance + sendingAmount;

                        // Update sender's balance
                        String updateSenderQuery = "UPDATE accounts SET balance = ? WHERE ID = ?";
                        PreparedStatement updateSenderStatement = conn.prepareStatement(updateSenderQuery);
                        updateSenderStatement.setDouble(1, newSenderBalance);
                        updateSenderStatement.setString(2, senderAccountNo);
                        updateSenderStatement.executeUpdate();

                        // Update receiver's balance
                        String updateReceiverQuery = "UPDATE accounts SET balance = ? WHERE ID = ?";
                        PreparedStatement updateReceiverStatement = conn.prepareStatement(updateReceiverQuery);
                        updateReceiverStatement.setDouble(1, newReceiverBalance);
                        updateReceiverStatement.setString(2, receiverAccountNo);
                        updateReceiverStatement.executeUpdate();

                        // Record the transaction in the transactions table
                        String insertTransactionQuery = "INSERT INTO transactions (sender_id, receiver_id, amount, transaction_datetime) VALUES (?, ?, ?, NOW())";
                        PreparedStatement insertTransactionStatement = conn.prepareStatement(insertTransactionQuery);
                        insertTransactionStatement.setString(1, senderAccountNo);
                        insertTransactionStatement.setString(2, receiverAccountNo);
                        insertTransactionStatement.setDouble(3, sendingAmount);
                        insertTransactionStatement.executeUpdate();
                        
                        out.println("Transaction successful!<br>");
                        out.println("Sender Account No: " + senderAccountNo + "<br>");
                        out.println("Receiver Account No: " + receiverAccountNo + "<br>");
                        out.println("Sending Amount: " + sendingAmount + "<br>");

                    } else {
                        out.println("Error: Insufficient funds in the sender's account.");
                    }
                } else {
                    out.println("Error: Sender or receiver account not found.");
                }

                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("Error: " + e.getMessage());
            }
        }
    }

    // Other methods...
}

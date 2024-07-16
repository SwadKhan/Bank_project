package Fundtransfer;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Fundtransfer2 extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webapp1";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        try (PrintWriter out = response.getWriter()) {
            String accountId = request.getParameter("AccountNo");

            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                String selectQuery = "SELECT * FROM transactions WHERE sender_id = ? OR receiver_id = ?";
                PreparedStatement statement = conn.prepareStatement(selectQuery);
                statement.setString(1, accountId);
                statement.setString(2, accountId);
                ResultSet resultSet = statement.executeQuery();

                out.println("<html><body>");
                out.println("<h1>Customer Transactions Details</h1>");
                // Define a CSS rule to right-align the content in the "Transaction Amount" column
                out.println("<style>");
                out.println(".right-align td:nth-child(3) {text-align: right;  padding-right:100px;}");/* 3 is the column index (0-based) */
                out.println("</style>");
                if (!resultSet.isBeforeFirst()) {
                    out.println("<h1>No transactions found for this account ID.</h1>");
                } else {
                    out.println("<table border='1' class='right-align'>");
                    out.println("<tr><th>Sender Account No</th><th>Receiver Account No</th><th>Transaction Amount</th><th>Transaction Date & Time</th></tr>");

                    while (resultSet.next()) {
                        // Retrieve and display data from the result set
                        String senderID = resultSet.getString("sender_id");
                        String receiverID = resultSet.getString("receiver_id");
                        String amount = resultSet.getString("amount");
                        String dateTime = resultSet.getString("transaction_datetime");

                        out.println("<tr>");
                        out.println("<td>" + senderID + "</td>");
                        out.println("<td>" + receiverID + "</td>");
                        out.println("<td>" + amount + "</td>");
                        out.println("<td>" + dateTime + "</td>");
                        out.println("</tr>");
                    }

                    out.println("</table>");
                }

                out.println("</body></html>");
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }

    // Other methods...
}

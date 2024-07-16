package Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webapp1";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            String Amount = request.getParameter("Amount");
            String  MobileNo = request.getParameter("MobileNo");
            String PolicyNo = request.getParameter("PolicyNo");
            String PurposeofPayment=request.getParameter("PurposeofPayment");
            //String id=request.getParameter("id");;
            String TransactionParticular = request.getParameter("TransactionParticular");
            String TransactionRemarks = request.getParameter("TransactionRemarks");
            String Radio = request.getParameter("Radio");

            try {
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                String insertQuery = "INSERT INTO customersdetails_form1 (Amount, MobileNo, PolicyNo,PurposeofPayment,TransactionParticular,TransactionRemarks,Radio) VALUES (?, ?, ?, ?, ?, ?, ?)";
                System.out.println("arrived");
                PreparedStatement statement = conn.prepareStatement(insertQuery);
               // statement.setString(1, id);
                statement.setString(1,Amount );
                statement.setString(2, MobileNo);
                statement.setString(3, PolicyNo);
                statement.setString(4,PurposeofPayment );
                statement.setString(5, TransactionParticular);
                statement.setString(6, TransactionRemarks);
                statement.setString(7, Radio);
                statement.executeUpdate();
                
                out.println("Successfully filled data for Insurance Premium Connection!<br>");
                out.println("Amount: " + Amount + "<br>");
                out.println("Mobile No: " + MobileNo + "<br>");
                out.println("Policy No: " + PolicyNo + "<br>");
                out.println("Purpose of Payment: " + PurposeofPayment + "<br>");
                out.println(" Transaction Particular: " + TransactionParticular + "<br>");
                out.println("Transaction Remarks: " + TransactionRemarks + "<br>");
                out.println("Radio: " + Radio + "<br>");

                // Retrieve the auto-generated ID
                
                conn.close();
            } catch (SQLException e) {
                 e.printStackTrace();
                out.println("Error: " + e.getMessage());
            }
        }
    }

    
      @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       response.setContentType("text/plain");
       
    try (PrintWriter out = response.getWriter()) {
        out.println("This is the doGet method of Customer Tab1.");
        // You can add any other logic or response you want here
    }
    }
    // Other methods...
}

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

public class FormServletTab3 extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webapp1";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String cw= request.getParameter("cw");

            try {
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                String insertQuery = "INSERT INTO customers_form3 (name, email, address, cw) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(insertQuery);
               // statement.setString(1, id);
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, address);
                statement.setString(4,cw );
                statement.executeUpdate();

                // Retrieve the auto-generated ID
                
                conn.close();
            } catch (SQLException e) {
                 e.printStackTrace();
                out.println("Error: " + e.getMessage());
            }
        }
    }

    // Other methods...
}

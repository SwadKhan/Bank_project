package Balance_inquiry;

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

public class Balance_inquiry extends HttpServlet {

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
                String selectQuery = "SELECT * FROM customers_form1 WHERE ID = ?";
                PreparedStatement statement = conn.prepareStatement(selectQuery);
                statement.setString(1, accountId);
                System.out.println("Got");
                ResultSet resultSet = statement.executeQuery();

                out.println("<html><body>");
                out.println("<h1>Customer Details</h1>");
                if (!resultSet.isBeforeFirst()) {
                    out.println("<h1>Invalid ID. No matching record found.</h1>");
                } else {

                    while (resultSet.next()) {
                        // Retrieve and display data from the result set
                        String name = resultSet.getString("name");
                        String email = resultSet.getString("email");
                        String address = resultSet.getString("address");

//                        out.println("Name: " + name);
//                        out.println("Email: " + email);
//                        out.println("Address: " + address);

                out.println("<p>Name: " + name + "</p>");
                out.println("<p>Email: " + email + "</p>");
                out.println("<p>Address: " + address + "</p>");
                    }

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

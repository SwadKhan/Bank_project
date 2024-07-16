package Billpay;

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

public class BillpayServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webapp1";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            // Retrieve data from the data string 'customData'
            String dataString = request.getParameter("st");

            // Split the data into individual records
            String[] records = dataString.split("\\|");

            // Initialize variables to store field values
            String firstName = "";
            String middleName = "";
            String lastName = "";
            String passport = "";
            String location = "";

            // Loop through the records and extract values for specific keys
            for (String record : records) {
                // Split each record into key-value pairs
                String[] keyValue = record.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    switch (key) {
                        case "firstName" ->
                            firstName = value;
                        case "middleName" ->
                            middleName = value;
                        case "lastName" ->
                            lastName = value;
                        case "passport" ->
                            passport = value;
                        case "location" ->
                            location = value;
                    }
                }
            }

            if (firstName.isEmpty() || middleName.isEmpty() || lastName.isEmpty() || passport.isEmpty() || location.isEmpty()) {
                out.println("Error: All fields are required.");
                return;
            }

            // Check passport length
            if (passport.length() < 3) {
                out.println("Error: Passport length must be at least 3 characters.");
                return;
            }

            try {
                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                    String insertQuery = "INSERT INTO billpay (firstName, middleName, lastName, passport, location) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement statement = conn.prepareStatement(insertQuery);
                    statement.setString(1, firstName);
                    statement.setString(2, middleName);
                    statement.setString(3, lastName);
                    statement.setString(4, passport);
                    statement.setString(5, location);
                    statement.executeUpdate();
                    out.println("<html><body>");
                    out.println("<h3>Successfully completed</h3>");
                    out.println("</body></html>");
                    out.println("First Name: " + firstName + "<br>");
                    out.println("Middle Name: " + middleName+ "<br>");
                    out.println("Last Name: " + lastName + "<br>");
                    out.println("Passport: " + passport+ "<br>");
                    out.println("Location: " + location + "<br>");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("Error: " + e.getMessage());
            }
        }
    }

    // Other methods...
}

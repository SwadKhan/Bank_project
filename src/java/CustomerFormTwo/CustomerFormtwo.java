/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CustomerFormTwo;

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

public class CustomerFormtwo extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webapp1";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        System.out.println("arrived2");
        try (PrintWriter out = response.getWriter()) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            int amount = Integer.parseInt(request.getParameter("amount"));
//            try {
//                amount = Integer.parseInt(request.getParameter("amount"));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            System.out.println("amount: " + amount);
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            try {
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                String insertQuery = "INSERT INTO customers_form2(name,email,amount,quantity) VALUES(?,?,?,?)";
                System.out.println("arrived");
                PreparedStatement statement = conn.prepareStatement(insertQuery);
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setInt(3, amount);
                statement.setInt(4, quantity);
                statement.executeUpdate();

                conn.close();

            } catch (SQLException e) {
                //  e.printStackTrace();
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

}

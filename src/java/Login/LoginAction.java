/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Struts/StrutsAction.java to edit this template
 */
package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author User
 */
public class LoginAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
  //  private static final String SUCCESS = "success";
     private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webapp1";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";
    

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    
//This line casts the form parameter to a specific form class, LoginActionBean, which is expected to hold the user's login data. In Struts, forms are used to capture and validate user input.
         LoginActionBean loginForm = (LoginActionBean) form; 



//    String data = "Some data to be stored"+loginForm.getUsername();
//    request.setAttribute("myData", data); // Setting a request-scoped attribute
//
//    HttpSession session = request.getSession();
//  //  Object user = null;
//    session.setAttribute("loggedInUser",session); // Setting a session-scoped attribute

    //authentication process is done here.
        
         if (authenticateUser(loginForm.getUsername(), loginForm.getPassword())) {
           //  System.out.println("arrived");
            // Redirect to success page
            return mapping.findForward("success");
        }else{
            // Redirect to failure page
            return mapping.findForward("failure");
        }
        
        //return mapping.findForward(SUCCESS);
    }
    
    
    
    
    private boolean authenticateUser(String username, String password) { 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//This line loads the MySQL JDBC driver class, which is necessary to connect to your MySQL database.
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//           InitialContext initialContext = new InitialContext();
//            DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/YourDataSourceName");
//
//            Connection connection = dataSource.getConnection();
//
//            InitialContext initialContext = new InitialContext();
//            DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/YourDataSourceName");

            boolean loginSuccess;
//            try (Connection connection = dataSource.getConnection()) {
            try (connection) {
                String query = "SELECT * FROM customers WHERE username=? AND password=?";
                
//Prepares a SQL statement with placeholders for the username and password values. The setString method is used to set the values for these placeholders.                
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    
//Executes the SQL query and obtains a ResultSet containing the results. The loginSuccess variable is set to true if there is at least one row in the result set (meaning a match was found for the username and password), otherwise, it remains false.                    
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        loginSuccess = resultSet.next();
                    }
                }
            }
            return loginSuccess;
        } //        } catch (NamingException | SQLException e) {
        //            return false;
        //        }
        catch (ClassNotFoundException | SQLException e) {
            // e.printStackTrace();
            return false;
        }

    }

    
}

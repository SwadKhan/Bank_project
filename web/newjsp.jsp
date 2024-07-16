<%-- 
    Document   : newjsp
    Created on : Aug 30, 2023, 4:01:17 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
         <c:out value="${requestScope.myData}" />
         <c:out value="${sessionScope.loggedInUser}" /> 
    </body>
</html>




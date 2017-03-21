<%-- 
    Document   : index.jsp
    Created on : Feb 27, 2017, 3:45:41 PM
    Author     : mavric
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Submit crisis information</h2>
        
        <jsp:forward page="/CrisisServlet?action=listCrisis" />
    </body>
</html>

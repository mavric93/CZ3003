<%-- 
    Document   : listCrisis
    Created on : Feb 27, 2017, 4:27:01 PM
    Author     : mavric
--%>

<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Show All Users</title>
</head>
<body>
    <table border=1>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
            </tr>
        </thead>
        <tbody>
            
            <c:forEach items="${crisisList}" var="crisis">
                <tr>
                    <td><c:out value="${crisis.crisisID}" /></td>
                    <td><c:out value="${crisis.crisisName}" /></td>
                    <td><a href="CrisisController?action=edit&crisisId=<c:out value="${crisis.crisisID}"/>">Update</a></td>
                    <td><a href="CrisisController?action=delete&crisisId=<c:out value="${crisis.crisisID}"/>">Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p><a href="CrisisController?action=insert">Add User</a></p>
</body>
</html>

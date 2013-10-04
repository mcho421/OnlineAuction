<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign In</title>
</head>
<body>
<c:if test ="${admin != null }">
<%@ include file="/WEB-INF/adminheader.jsp" %>
</c:if>
<c:if test ="${admin == null }">
<%@ include file="/WEB-INF/header.jsp" %>
</c:if>
<form name="loginform" action="controller" method = "post">
<input type="hidden" name="action" value="login"/>
<table>
<tr>
<td>Enter User Name:</td><td> <input type = "text" name="username"></td>
</tr>
<tr>
<td>Enter Password:</td><td> <input name="password" type="password"></td>
</tr>
<tr><td><font color =red>${msg}</font></td></tr>
<tr>
<td><input type="submit" value ="Sign In"></td>
<td><input type="button" onclick="window.location.href='controller?action=registerPage'" value ="Sign Up Now"></td>
</tr>
</table>
</form>
</body>
</html>

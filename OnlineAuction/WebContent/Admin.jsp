<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
Object username=session.getAttribute("username"); 
%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin</title>
</head>
<body>
<%@ include file="/WEB-INF/header.jsp" %>
<table>
<tr>
<td><font size = '4' color =red>Hello, adminastrator,<%=username%></font></td>
</tr>
<tr>
<td><input type="button" onclick="window.location.href='AdminItems.jsp'" value ="View Items On Auction"></td>
<td><input type="button" onclick="window.location.href='AdminUsers.jsp'" value ="View Users"></td>
<td><input type="button" onclick="window.location.href='Message.jsp'" value ="message"></td>
</tr>
</table>
</body>
</html>
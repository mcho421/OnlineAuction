<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<font size = '4' color =red>Hello, administrator,${username}</font><br>
<c:if test="${username != null}">
	Logged in as <a href="controller?action=adminpage">${username}</a> | <a href="controller?action=logout">Logout</a>
</c:if>
<a href="controller">Home</a>
<hr>
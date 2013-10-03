<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${username != null}">
	Logged in as <a href="controller?action=userIndexPage">${username}</a> | <a href="controller?action=logout">Logout</a>
</c:if>
<c:if test="${username == null}">
	<a href="controller?action=loginPage">Login</a> | <a href="controller?action=registerPage">Register</a>
</c:if>
| <a href="controller">Home</a>
<hr>
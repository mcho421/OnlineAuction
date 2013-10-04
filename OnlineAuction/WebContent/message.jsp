<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Message</title>
</head>
<%@ include file="/WEB-INF/header.jsp" %>
<body>
<table width="60%" border="1">
	<tr>
		<th width="50%">Message</th>
		<th width="30%">Time</th>
		<th width="20%">Read</th>
	</tr>
	<c:forEach var="r" items="${result}">
		<tr>
		<td width="50%">${r.message}</td>
		<td width="30%">${r.time}</td>
			<td width="20%">
				<c:if test="${r.read}">Read</c:if>
				<c:if test="${!r.read}">Unread</c:if>
			</td>

		</tr>
	</c:forEach>	
</table>
<table>
<tr><td><a href="controller?action=deletemsg">Delete All Messages</a></td></tr>
</table>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:query var="rs" dataSource="jdbc/database">
select id, category from Categories
</sql:query>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Auction Site</title>
</head>
<body>
<c:if test ="${admin != null }">
<%@ include file="/WEB-INF/adminheader.jsp" %>
</c:if>
<c:if test ="${admin == null }">
<%@ include file="/WEB-INF/header.jsp" %>
</c:if>
<h1>Welcome to the Auction Site!</h1>
Search:
<form name="search" action="controller" method="GET">
	<input type="hidden" name="action" value="search"/>
	<table>
	<tr>
		<td><input type="text" name="item"/></td>
		<td>
			<select name="category">
				<option value="0" selected>All Categories</option>
				<c:forEach var="row" items="${rs.rows}">
					<option value="${row.id}">${row.category}</option>
				</c:forEach>
			</select>
		</td>
		<td><input type="submit" name="submitSearch" value="Submit"></td>
	</tr>
	</table>
</form>
</body>
</html>
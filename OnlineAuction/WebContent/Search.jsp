<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Auction Site</title>
</head>
<body>
<%@ include file="/WEB-INF/header.jsp" %>

<h1>Search Results:</h1>
<table width="80%" border="1">
	<tr>
		<th width="30%">Image</th>
		<th width="30%">Item</th>
		<th width="20%">Current Bidding Price</th>
		<th width="20%">Closing time</th>
	</tr>
	<c:forEach var="r" items="${result}">
		<tr>
			<td width="30%">
				<c:if test="${r.picturePath != null}">
					<a href="controller?action=itemPage&item=${r.id}">
						<img src="ImageServlet?image=${r.picturePath}" style="max-width: 200px; max-height: 200px;">
					</a>
			    </c:if>
			</td>
			<td width="30%"><a href="controller?action=itemPage&item=${r.id}"><b>${r.title}</b></a><br>${r.description}</td>
			<td width="20%">$ ${r.currentBiddingPrice}</td>
			<td width="20%" >${r.timeLeft}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>

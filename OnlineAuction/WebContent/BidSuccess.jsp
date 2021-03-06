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

<h1>Bid Success!</h1>
You have successfully placed a bid on the following item:
<br><br>
<table>
	<tr>
		<td>
			<c:if test="${item.picturePath != null}">
				<img src="ImageServlet?image=${item.picturePath}" style="max-width: 100px; max-height: 100px;">
		    </c:if>
    	</td>
    	<td>
			<h2>${item.title}</h2>
			<h2>Your bid: $ ${bid.bid}</h2>
    	</td>
    </tr>

	<tr>
		<td>
			<form name="backToItem" action="controller" method="get">
				<input type="hidden" name="action" value="itemPage"/>
				<input type="hidden" name="item" value="${item.id}"/>
				<input type="submit" value="Back to item">
			</form>
    	</td>
    	<td>
			<form name="userIndex" action="controller" method="post">
				<input type="hidden" name="action" value="userIndexPage"/>
				<input type="submit" value="User Profile">
			</form>
    	</td>
    </tr>
</table>



</body>
</html>
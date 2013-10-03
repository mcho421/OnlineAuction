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

<h1>${item.title}</h1>
<table>
	<tr>
		<td>
			<c:if test="${item.picturePath != null}">
				<img src="ImageServlet?image=${item.picturePath}" style="max-width: 200px; max-height: 200px;">
		    </c:if>
		</td>
		<td>
			<table>
				<tr><td><b>Time left: </b>${item.timeLeft}</td></tr>
				<tr><td><b>Current bid: $ ${item.currentBiddingPrice}</b></td></tr>
				<tr><td>
					<b>Make a bid: $</b>
					<form name="bidform" action="controller" method="post">
						<input type="hidden" name="action" value="makeBid"/>
						<input type="hidden" name="item" value="${item.id}"/>
						<input type="text" name="bidAmount">
						<input type="submit" value ="Bid!">
					</form>
							
				</td></tr>
				<tr><td>Minimum bid $ ${item.minimumBid} or more</td></tr>
			</table>
		</td>
	</tr>
</table>
<h2>Description</h2>
${item.description}
</body>
</html>
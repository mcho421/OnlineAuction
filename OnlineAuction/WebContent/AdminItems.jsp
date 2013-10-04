<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<sql:query var="rs" dataSource="jdbc/database">
SELECT * FROM items  where closingtime > CURRENT_TIMESTAMP
</sql:query>
<% 
Object username=session.getAttribute("username"); 
%> 
<html>
<head>
<title>Users</title>
</head>
<body>

<%@ include file="/WEB-INF/header.jsp" %>
<table>
<tr>
<td><input type="button" onclick="window.location.href='AdminItems.jsp'" value ="View Items On Auction"></td>
<td><input type="button" onclick="window.location.href='AdminUsers.jsp'" value ="View Users"></td>
</tr>
<tr>
<td><font size = '4' color =red>Hello, adminastrator,<%=username%></font></td>
</tr>
</table>
<br/><font size = '4' color =red><%=request.getAttribute("msg")%></font><br/>
<table width="80%" border="1">
	<tr>
		<th width="30%">Image</th>
		<th width="25%">Item</th>
		<th width="10%">Reserve Price</th>
		<th width="20%">Closing time</th>
		<th width="15%">Halt</th>
	</tr>
	<c:forEach var="row" items="${rs.rows}">
		<tr>
			<td width="30%">
				<c:if test="${row.picturePath != null}">
						<img src="ImageServlet?image=${r.picturePath}" style="max-width: 200px; max-height: 200px;">
			    </c:if>
			</td>
			<td width="25%"><b>${row.title}</b><br>${row.description}</td>
			<td width="10%">$ ${row.reserveprice}</td>
			<td width="20%" >${row.closingtime}</td>
			<td width="15%"><a href="controller?action=haltitem&itemid=${row.id}"><b>Halt It!</b></a></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
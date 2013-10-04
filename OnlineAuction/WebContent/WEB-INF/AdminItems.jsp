<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<sql:query var="rs" dataSource="jdbc/database">
SELECT * FROM items where closingtime > CURRENT_TIMESTAMP
</sql:query>
<% 
Object username=session.getAttribute("username"); 
%> 
<html>
<head>
<title>Users</title>
</head>
<body>

<%@ include file="/WEB-INF/adminheader.jsp" %>
<table>
<tr>
<td><a href="controller?action=adminitemspage">View Items On Auction</a></td>
</tr>
<tr>
<td><a href="controller?action=adminuserspage">View Users</a></td>
</tr>
</table>
<c:if test ="${msg!=null }">
<br/><font size = '4' color =red><%=request.getAttribute("msg")%></font><br/>
</c:if>
<table width="80%" border="1">
	<tr>
		<th width="30%">Image</th>
		<th width="25%">Item</th>
		<th width="8%">Reserve Price</th>
		<th width="17%">Closing time</th>
		<th width="5%">Halted</th>
		<th width="15%">Halt</th>
	</tr>
	<c:forEach var="row" items="${rs.rows}">
		<tr>
			<td width="30%">
				<c:if test="${row.picturePath != null}">
						<img src="ImageServlet?image=${row.picturePath}" style="max-width: 200px; max-height: 200px;">
			    </c:if>
			</td>
			<td width="25%"><b>${row.title}</b><br>${row.description}</td>
			<td width="8%">$ ${row.reserveprice}</td>
			<td width="17%" >${row.closingtime}</td>
			<td width="5%"> ${row.halted }</td>
			<td width="15%"><a href="controller?action=haltitem&itemid=${row.id}"><b>Halt It!</b></a></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
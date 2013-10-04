<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<sql:query var="rs" dataSource="jdbc/database">
select id, username , email, confirmed, status from Users where status < 2
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
<td><a href="controller?action=adminuserspage">View Users"</a></td>
</tr>
</table>
<c:if test ="${msg!=null }">
<br/><font size = '4' color =red><%=request.getAttribute("msg")%></font><br/>
</c:if>
<table width="80%" border="1">
	<tr>
		<th width="5%">id</th>
		<th width="20%">username</th>
		<th width="50%">email</th>
		<th width="8%">confirmed</th>
		<th width="5%">status</th>
		<th width="12%">Ban</th>
	</tr>
	<c:forEach var="row" items="${rs.rows}">
		<tr>
			<td width="5%">${row.id}</td>
			<td width="20%">${row.username}</td>
			<td width="50%">${row.email}</td>
			<td width="8%" >${row.confirmed}</td>
			<td width="5%" >${row.status}</td>
			<td width="12%" ><a href="controller?action=banuser&userid=${row.id}"><b>Ban Him!</b></a></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
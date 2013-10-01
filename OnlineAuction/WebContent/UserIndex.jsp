<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="UserBean" class="group.UserBean"
scope="request" />
<jsp:setProperty property="*" name="UserBean" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Index</title>
</head>
<body>
<% 
Object username=session.getAttribute("username"); 
%> 
<script language="javascript">
if(username  =="") {
	window.location.href=basePath+'Login.jsp';
}
</script>
<table>
<tr>
<td><font size = '6' color =red>"<%=username%>"</font></td>
</tr>
<tr>
<td>Offer an item</td>
<td>items on auction</td>
<td>items on auction</td>
<td>items on auction</td>
</tr>
</table>
<form name="form1" method="post" action="register" onSubmit="return on_submit()">

<table>

<tr>
<td>Username</td>
<td><input type = "text" name = "txtUname" value="<%=UserBean.getUsername()%>"/></td>
</tr>

<tr>
<td>Password</td>
<td><input type = "password" name = "txtUpwd" value="<%=UserBean.getUserpwd()%>"/></td>
</tr>
<tr>
<td>Reenter Password </td>
<td><input type = "password" name = "txtUpwd2" value="<%=UserBean.getUserpwd()%>"/></td>
<td><font color =red><%=UserBean.getErrorMsg("password")%></font></td>
</tr>
<tr>
<td>Email</td>
<td><input type = "text" name = "txtUemail" value="<%=UserBean.getUseremail()%>"/></td>
<td><font color =red><%=UserBean.getErrorMsg("email")%></font></td>
</tr>
<tr>
<td><input type ="submit" value="Continue" ></td>
<td></td>
</tr>
<tr>
<td>First Name</td>
<td><input type = "text" name = "txtUemail" value="<%=UserBean.getFname()%>"/></td>
<td><font color =red><%=UserBean.getErrorMsg("email")%></font></td>
</tr>
<tr>
<td><input type ="submit" value="Continue" ></td>
<td></td>
</tr>
<tr>
<td>Last Name</td>
<td><input type = "text" name = "txtUemail" value="<%=UserBean.getLname()%>"/></td>
<td><font color =red><%=UserBean.getErrorMsg("email")%></font></td>
</tr>
<tr>
<td><input type ="submit" value="Continue" ></td>
<td></td>
</tr>
<tr>
<td>Year of Birth</td>
<td><input type = "text" name = "txtUemail" value="<%=UserBean.getYearofbrirth()%>"/></td>
<td><font color =red><%=UserBean.getErrorMsg("email")%></font></td>
</tr>
<tr>
<td><input type ="submit" value="Continue" ></td>
<td></td>
</tr>
<tr>
<td>Address</td>
<td><input type = "text" name = "txtUemail" value="<%=UserBean.getFulladdress()%>"/></td>
<td><font color =red><%=UserBean.getErrorMsg("email")%></font></td>
</tr>
<tr>
<td><input type ="submit" value="Continue" ></td>
<td></td>
</tr>
<tr>
<td>Address</td>
<td><input type = "text" name = "txtUemail" value="<%=UserBean.getCreditcard()%>"/></td>
<td><font color =red><%=UserBean.getErrorMsg("email")%></font></td>
</tr>
<tr>
<td><input type ="submit" value="Continue" ></td>
<td></td>
</tr>

</table>
</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import = "group.UserBean" %>
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
if(username==null) {
	System.out.println("log in");
	response.sendRedirect("\Login.jsp");
	//RequestDispatcher rd = null;
	//rd = sc.getRequestDispatcher("/Login.jsp"); 
	//rd.forward(request, response);
	//window.location.href=basePath+'Login.jsp';
}
function on_submit()
{
 if (updateform.txtFname.value == "")
   {
    alert("Enter Your First Name");
    updateform.txtFname.focus();
    return false;
   }

 if (updateform.txtUpwd.value == "")
   {
    alert("Enter Your Password");
    updateform.txtUpwd.focus();
    return false;
   }
   if (updateform.txtLname.value == "")
   {
    alert("Enter Your Last Name");
    updateform.txtLname.focus();
    return false;
   }
   if (updateform.txtYOB.value == "")
   {
    alert("Enter Your Year of Birth");
    updateform.txtYOB.focus();
    return false;
   }
   if (updateform.txtAddress.value == "")
   {
    alert("Enter Your Full Address");
    updateform.txtAddress.focus();
    return false;
   }
   if (updateform.txtCCN.value == "")
   {
    alert("Enter Your Credit Card Number");
    updateform.txtCCN.focus();
    return false;
   }
}
</script>
<table>
<tr>
<td><font size = '4' color =red>Hello, <%=username%></font></td>
</tr>
<tr>
<td><input type="button" onclick="window.location.href='OfferItem.jsp'" value ="Offer an item"></td>
<td><input type="button" onclick="window.location.href='Login.jsp'" value ="items on auction"></td>
<td><input type="button" onclick="window.location.href='Register.jsp'" value ="message"></td>
<td><input type="button" onclick="window.location.href='Register.jsp'" value ="logout"></td>
</tr>
</table>
<form name="updateform" method="post" action="controller" onSubmit="return on_submit()">
<input type="hidden" name="action" value="updateUser"/>
<table>
<tr>
<td>Username</td>
<td><%=UserBean.getUsername()%></td>
</tr>
<tr>
<td>Password</td>
<td><input type = "password" name = "txtUpwd" value="<%=UserBean.getUserpwd()%>"/></td>
</tr>
<tr>
<td>Email</td>
<td><input type = "text" name = "txtUemail" value="<%=UserBean.getUseremail()%>"/></td>
<td><font color =red><%=UserBean.getErrorMsg("email")%></font></td>
</tr>
<tr>
<td>First Name</td>
<td><input type = "text" name = "txtFname" value="<%=UserBean.getFname()%>"/></td>
<td><font color =red><%=UserBean.getErrorMsg("fname")%></font></td>
</tr>
<tr>
<td>Last Name</td>
<td><input type = "text" name = "txtLname" value="<%=UserBean.getLname()%>"/></td>
<td><font color =red><%=UserBean.getErrorMsg("lname")%></font></td>
</tr>
<tr>
<td>Year of Birth</td>
<td><input type = "text" name = "txtYOB" value="<%=UserBean.getYearofbirth()%>"/></td>
<td><font color =red><%=UserBean.getErrorMsg("yearofbirth")%></font></td>
</tr>
<tr>
<td>Address</td>
<td><input type = "text" name = "txtAddress" value="<%=UserBean.getFulladdress()%>"/></td>
<td><font color =red><%=UserBean.getErrorMsg("address")%></font></td>
</tr>
<tr>
<td>Credit Card Number</td>
<td><input type = "text" name = "txtCCN" value="<%=UserBean.getCreditcard()%>"/></td>
<td><font color =red><%=UserBean.getErrorMsg("creditcard")%></font></td>
</tr>
<tr>
<td><input type ="submit" value="Update" ></td>
<td></td>
</tr>

</table>
</form>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String path =request.getContextPath();
String basePath =request.getScheme() + "://"
+ request.getServerName() +":" + request.getServerPort()
+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="RegisterForm" class="group.RegisterForm"
scope="request" />
<jsp:setProperty property="*" name="RegisterForm" />
<script language="javascript">
function on_submit()
 {
  if (form1.txtUname.value == "")
    {
     alert("Enter Your User Name");
     form1.txtUname.focus();
     return false;
    }

  if (form1.txtUpwd.value == "")
    {
     alert("Enter Your Password");
     form1.txtUpwd.focus();
     return false;
    }
    if (form1.txtUpwd2.value == "")
    {
     alert("Reenter Your Password");
     form1.txtUpwd2.focus();
     return false;
    }
   if (form1.txtUpwd.value != form1.txtUpwd2.value)
    {
    alert("These passwords don't match");
    form1.txtUpwd.focus();
     return false;
    }
   
 }
</script>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up</title>
</head>
<body>
<form name="form1" method="post" action="register" onSubmit="return on_submit()">
<table>

<tr>
<td>Username</td>
<td><input type = "text" name = "txtUname" value="<%=RegisterForm.getUsername()%>"/></td>
<td><font color =red><%=RegisterForm.getErrorMsg("name")%></font></td>
</tr>

<tr>
<td>Password</td>
<td><input type = "password" name = "txtUpwd" value="<%=RegisterForm.getUserpwd()%>"/></td>
</tr>
<tr>
<td>Reenter Password </td>
<td><input type = "password" name = "txtUpwd2" value="<%=RegisterForm.getUserpwd()%>"/></td>
<td><font color =red><%=RegisterForm.getErrorMsg("password")%></font></td>
</tr>
<tr>
<td>Email</td>
<td><input type = "text" name = "txtUemail" value="<%=RegisterForm.getUseremail()%>"/></td>
<td><font color =red><%=RegisterForm.getErrorMsg("email")%></font></td>
</tr>
<tr>
<td><input type ="submit" value="Continue" ></td>
<td></td>
</tr>


</table>
</form>
</body>
</html>
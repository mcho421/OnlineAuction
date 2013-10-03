<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="Item" class="group.Item"
scope="session" />
<jsp:setProperty property="*" name="Item" />
<% 
Object username=session.getAttribute("username"); 
String path = getServletContext().getRealPath("/");
System.out.println("0-0");
System.out.println(path);
%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload Picture</title>
</head>
<body>
<table>
<tr><td><font size = '4' color =red>Hello, <%=username%></font></td></tr>
<tr>
<td>Title</td>
<td>${Item.getTitle()}</td>
</tr>
<tr>
<td>Category</td>
<td>${Item.getCategory()}</td>
</tr>
<tr>
<td>Descrpition</td>
<td >${Item.getDescription()}</td>
</tr>
<tr>
<td>Postage Details</td>
<td>${Item.getPostage()}</td>
</tr>
<tr>
<td>Reserve Price</td>
<td>${Item.getRprice()}</td>
</tr>
<tr>
<td>Bidding Start Price</td>
<td>${Item.getSprice()}</td>
</tr>
<tr>
<td>Bidding Increments</td>
<td>${Item.getBincre()}</td>
</tr>
<tr>
<td>Closing Time</td>
<td>${Item.getCtime()}</td>
</tr>
</table>
<form name="upform" action = "upload?path=<%=path%>" method ="post" enctype = "multipart/form-data">
<table>
<tr>
<td>Picture</td>
<td><input type = "file" style="width:500px;height:20px;" name = "iamge" /></td>
<td><font color =red><%=Item.getErrorMsg("picture") %></font></td>
</tr>
<tr><td><input type = "submit" name="Start Auction"/></td></tr>
</table>
</form>
</body>
</html>
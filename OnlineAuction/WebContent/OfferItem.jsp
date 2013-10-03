<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id= "Item" class="group.Item"
scope="request" />
<jsp:setProperty property="*" name="Item" />
<% 
Object username=session.getAttribute("username"); 
String path = getServletContext().getRealPath("/");
%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Offer An Item</title>
</head>

<body>
<%@ include file="/WEB-INF/header.jsp" %>
<form name ="offeritem" method = "post" action = "controller" >
<input type="hidden" name="action" value="offeritem"/>
<table>
<tr>
<td><font size = '4' color =red>Hello, <%=username%></font></td>
</tr>
<tr>
<td>Title</td>
<td><input type = "text" style="width:500px;height:20px;" name = "title" value ="<%=Item.getTitle()%>" /></td>
<td><font color =red><%=Item.getErrorMsg("title") %></font></td>
</tr>
<tr>
<td>Category</td>
<td><select name="category" style="width:300px;height:20px;">
  <option value="1" selected>Antiques</option>
  <option value="2">Books</option>
  <option value="3">Clothing</option>
  <option value="4">Electronics</option>
  <option value="5">Other</option>
</select></td>
</tr>
<tr>
<td>Descrpition</td>
<td ><input style="width:500px;height:200px;" type = "text" name = "description"value="<%=Item.getDescription() %>" /></td>
<td><font color =red><%=Item.getErrorMsg("descrption") %></font></td>
</tr>
<tr>
<td>Postage Details</td>
<td><input type = "text" style="width:500px;height:20px;"name = "postage" value = "<%=Item.getPostage() %>" /></td>
<td><font color =red><%=Item.getErrorMsg("postage") %></font></td>
</tr>
<tr>
<td>Reserve Price</td>
<td><input type = "text" style="width:500px;height:20px;"name = "rprice"value = "${Item.getRprice()}"/></td>
<td><font color =red><%=Item.getErrorMsg("rprice") %></font></td>
</tr>
<tr>
<td>Bidding Start Price</td>
<td><input type = "text" style="width:500px;height:20px;"name = "sprice"value = "${Item.getSprice()}"/></td>
<td><font color =red><%=Item.getErrorMsg("sprice") %></font></td>
</tr>
<tr>
<td>Bidding Increments</td>
<td><input type = "text"style="width:500px;height:20px;" name = "bincre"value = "${ tem.getBincre()}"/></td>
<td><font color =red><%=Item.getErrorMsg("iprice") %></font></td>
</tr>
<tr>
<td>Closing Time</td>
<td><select name="closingtime" style="width:300px;height:20px;">
  <option value="1" selected>1 hour later</option>
  <option value="2">2 hour later</option>
  <option value="3">3 hour later</option>
</select></td>
</tr>
<tr>
<td><input type = "submit" value = "Upload Picture"/></td>
</tr>
</table>
</form>
</body>
</html>

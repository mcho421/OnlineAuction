<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Online Auction</title>
</head>
<body>
<c:if test ="${admin != null }">
<%@ include file="/WEB-INF/adminheader.jsp" %>
</c:if>
<c:if test ="${admin == null }">
<%@ include file="/WEB-INF/header.jsp" %>
</c:if>
<h1> An Error Occurred! </h1>
${errorMsg}
<h2> Return to the home page ? </h2>
<form action="controller">
	<input type="submit" name="goBack" value="Home"/>
</form>
</body>
</html>
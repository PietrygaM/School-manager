<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../jspf/headconfig.jspf" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>REGISTER</title>
</head>
<body>

<%@ include file="../jspf/header.jspf" %>

	<form:form method="post" modelAttribute="user">
	<p>Email <form:input path="email"/><form:errors path="email"/></p>
	<p>Password <form:password path="password"/><form:errors path="password"/></p>
	<p><input type="submit"/></p>	
	</form:form>

<%@ include file="../jspf/footer.jspf" %>

</body>
</html>
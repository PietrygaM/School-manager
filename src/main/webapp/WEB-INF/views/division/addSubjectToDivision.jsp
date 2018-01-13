<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page session="true"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../jspf/headconfig.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>School manage-system</title>
</head>
<body>

	<%@ include file="../jspf/header.jspf"%>

<div class="container">	

	<div class="panel panel-default">
			<div class="panel-heading">
				<h3>List of all subjects in: ${divisionParams.name}</h3>
			</div>
			<div class="panel-body">
	
	
	<table class="table table-striped">
		<thead>
			<tr>
				<th scope="col">ID</th>
				<th scope="col">NAME</th>
				<th scope="col">DESCRIPTION</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${allSubjectsInGroup}" var="subject">
				<tr>
					<td scope="row"><c:out value="${subject.id}" /></td>
					<td><c:out value="${subject.name}" /></td>
					<td><c:out value="${subject.description}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	

	<form:form method="post" modelAttribute="addToDivision">
		<form:select type="text" path="id">
			<form:options items="${allSubjectsNotInGroup}" itemValue="id"
				itemLabel="name" />
		</form:select>
		<input type="submit" value="add">
	</form:form>



</div>

	<%@ include file="../jspf/footer.jspf"%>

</body>
</html>
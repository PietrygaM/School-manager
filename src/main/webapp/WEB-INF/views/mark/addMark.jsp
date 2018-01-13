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
				<h4>Add new mark</h4>
			</div>
			<div class="panel-body">

				<c:choose>
					<c:when test="${sessionScope.user.role=='ROLE_TEACHER'}">


						<form:form method="post" modelAttribute="mark">
							<h4>Add new mark for ${mark.student.firstName}</h4>
							<table>
								<tr>
									<td>Value:</td>
									<td><form:input path="value" /> <form:errors path="value" /></td>
								</tr>
								<tr>
									<td>Date:</td>
									<td></td>
								</tr>
								<tr>
									<td>Description:</td>
									<td><form:textarea path="description" /> <form:errors
											path="description" /></td>
								</tr>
								<tr>
									<td><input type="submit" value="Add"
										class="btn btn-primary"></td>
									<td></td>
								</tr>
							</table>

						</form:form>

					</c:when>
					<c:otherwise>
						<%@ include file="../jspf/permission_denied.jspf"%>
					</c:otherwise>
				</c:choose>

			</div>
		</div>
	</div>

	<%@ include file="../jspf/footer.jspf"%>

</body>
</html>
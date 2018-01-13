<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../jspf/headconfig.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>LOGIN</title>
</head>
<body>

	<%@ include file="../jspf/header.jspf"%>

	<div class="container">

		<div class="panel panel-default">
			<div class="panel-heading">
				<h4>Login</h4>
				<p class='error'>${msg}</p>
			</div>
			<div class="panel-body">


				<form:form method="post" modelAttribute="loginData">
					<table>
						<tr>
							<td>Email:</td>
							<td><form:input path="email" />
								<form:errors path="email" /></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><form:password path="password" />
								<form:errors path="password" /></td>
						</tr>
						<tr>
							<td><input type="submit" value="Login" /></td>
						</tr>
					</table>
				</form:form>

			</div>
		</div>
	</div>

	<%@ include file="../jspf/footer.jspf"%>

</body>
</html>
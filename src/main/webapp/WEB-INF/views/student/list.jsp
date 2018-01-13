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
				<h4>List of all students</h4>
			</div>
			<div class="panel-body">

				<c:choose>
					<c:when test="${sessionScope.user.role=='ROLE_TEACHER'}">

							<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">ID</th>
									<th scope="col">EMAIL</th>
									<th scope="col">FIRST NAME</th>
									<th scope="col">LAST NAME</th>
									<th scope="col">OPTION</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${allStudents}" var="student">
									<tr>
										<td scope="row"><c:out value="${student.id}" /></td>
										<td><c:out value="${student.email}" /></td>
										<td><c:out value="${student.firstName}" /></td>
										<td><c:out value="${student.lastName}" /></td>
										<td><c:choose>
												<c:when test="${del eq student.id}">
													<form:form method="post" modelAttribute="student">
														<%@ include file="../jspf/delete.jspf"%>
													</form:form>
												</c:when>
												<c:otherwise>
													<div class="dropdown">
														<button class="btn btn-primary dropdown-toggle" id="menu1"
															type="button" data-toggle="dropdown">
															Option <span class="caret"></span>
														</button>
														<ul class="dropdown-menu" role="menu"
															aria-labelledby="menu1">
															<li role="presentation"><a role="menuitem"
																tabindex="-1"
																href="${pageContext.request.contextPath}/student/${student.id}/edit">Edit</a></li>
															<li role="presentation"><a role="menuitem"
																tabindex="-1"
																href="${pageContext.request.contextPath}/student/${student.id}/delete">Delete</a></li>
														</ul>
													</div>
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>


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
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

		<c:choose>
			<c:when test="${sessionScope.user.role=='ROLE_TEACHER'}">

				<div class="panel panel-default">
					<div class="panel-heading">
						Choose subject:
						<div class="dropdown">
							<button class="btn btn-primary dropdown-toggle" id="menu1"
								type="button" data-toggle="dropdown">
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
								<c:forEach items="${allSubjectsInGroup}" var="subject">
									<li role="presentation"><a role="menuitem" tabindex="-1"
										href="${pageContext.request.contextPath}/division/newscast?divisionId=${divisionId}&subjectId=${subject.id}">${subject.name}</a></li>
								</c:forEach>
							</ul>
						</div>


					</div>
					<div class="panel-body">

						<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">ID</th>
									<th scope="col">FIRST NAME</th>
									<th scope="col">LAST NAME</th>
									<th scope="col">MARKS</th>
									<th scope="col">OPTION</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${allStudentsInGroup}" var="student">
									<tr>
										<td scope="row"><c:out value="${student.id}" /></td>
										<td><c:out value="${student.firstName}" /></td>
										<td><c:out value="${student.lastName}" /></td>
										<td></td>
										<td></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

			</c:when>
			<c:otherwise>
				<%@ include file="../jspf/permission_denied.jspf"%>
			</c:otherwise>
		</c:choose>

	</div>
	<%@ include file="../jspf/footer.jspf"%>
</body>
</html>
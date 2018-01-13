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
				<h4>Mark details</h4>
			</div>
			<div class="panel-body">

				<c:choose>
					<c:when test="${sessionScope.user.role=='ROLE_STUDENT'}">

						<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">ID</th>
									<th scope="col">VALUE</th>
									<th scope="col">DESCRIPTION</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${allStudentMarksFromSubject}" var="mark">
									<tr>
										<td scope="row"><c:out value="${mark.id}" /></td>
										<td><c:out value="${mark.value}" /></td>
										<td><c:out value="${mark.description}" /></td>
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
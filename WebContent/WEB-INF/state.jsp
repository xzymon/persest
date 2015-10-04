<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>State</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
</head>
<body>
	<h1>Application State Report</h1>
	<h2>URL</h2>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Protocol</th>
				<th>ServerName</th>
				<th>Port</th>
				<th>ServletPath</th>
				<th>QueryString</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${requestScope.stateBean.urlParts.protocol}</td>
				<td>${requestScope.stateBean.urlParts.serverName}</td>
				<td>${requestScope.stateBean.urlParts.port}</td>
				<td>${requestScope.stateBean.urlParts.servletPath}</td>
				<td>${requestScope.stateBean.urlParts.queryString}</td>
			</tr>
		</tbody>
	</table>
	<h2>Headers</h2>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Values</th>
			</tr>
		</thead>
		<tbody>
			<!-- Works hen var="headr". Doesn't work when var="header". Why? -->
			<c:forEach var="headr" items="${requestScope.stateBean.headers}">
			<tr>
				<td>${headr.name}</td>
				<td>
					<c:forEach var="value" items="${headr.values}" varStatus="loopTag">
						<c:if test="${loopTag.index gt 0}">
							<br />
						</c:if>
						${value}
					</c:forEach>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<h2>Cookies</h2>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Domain</th>
				<th>Path</th>
				<th>Max Age</th>
				<th>Http Only?</th>
				<th>Secure</th>
				<th>Version</th>
				<th>Value</th>
				<th>Comment</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="cookie" items="${requestScope.stateBean.cookies}">
			<tr>
				<td>${cookie.name}</td>
				<td>${cookie.domain}</td>
				<td>${cookie.path}</td>
				<td>${cookie.maxAge}</td>
				<td>${cookie.httpOnly}</td>
				<td>${cookie.secure}</td>
				<td>${cookie.version}</td>
				<td>${cookie.value}</td>
				<td>${cookie.comment}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<h2>Attributes</h2>
	<h3>Application (ServletContext)</h3>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Value</th>
				<th>Class</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="attr" items="${requestScope.stateBean.applicationAttributes}">
			<tr>
				<td>${attr.name}</td>
				<td>${attr.getValueAsStringWithMaxLengthFiltering(200)}</td>
				<td>${attr.runtimeType}</td>
			</tr>
			</c:forEach>
		</tbody>
		<tfoot></tfoot>
	</table>
	<h3>Session</h3>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Value</th>
				<th>Class</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="attr" items="${requestScope.stateBean.sessionAttributes}">
			<tr>
				<td>${attr.name}</td>
				<td>${attr.getValueAsStringWithMaxLengthFiltering(200)}</td>
				<td>${attr.runtimeType}</td>
			</tr>
			</c:forEach>
		</tbody>
		<tfoot></tfoot>
	</table>
	<h3>Request</h3>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Value</th>
				<th>Class</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="attr" items="${requestScope.stateBean.requestAttributes}">
			<tr>
				<td>${attr.name}</td>
				<td>${attr.getValueAsStringWithMaxLengthFiltering(200)}</td>
				<td>${attr.runtimeType}</td>
			</tr>
			</c:forEach>
		</tbody>
		<tfoot></tfoot>
	</table>
</body>
</html>
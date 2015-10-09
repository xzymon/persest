<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Units</title>
</head>
<body>
<h1>Units</h1>
<table>
	<thead>
		<tr>
			<th>id</th>
			<th>name</th>
			<th>code</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="unit" items="${requestScope.units}">
		<tr>
			<td>${unit.id}</td>
			<td>${unit.name}</td>
			<td>${unit.code}</td>
			<td>
				<form method="post" action="">
					<input type="hidden" name="du" value="${unit.id}">
					<input type="submit" value="Delete">
				</form>
			</td>
		</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</tfoot>
</table>

<form method="POST" action="">
	<table>
		<tr>
			<td>Name:</td>
			<td><input type="text" name="nun"></td>
		</tr>
		<tr>
			<td>Code:</td>
			<td><input type="text" name="nuc"></td>
		</tr>
	</table>
	<input type="submit" value="Send">
</form>
</body>
</html>
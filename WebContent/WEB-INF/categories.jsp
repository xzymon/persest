<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Categories</title>
</head>
<body>
<h1>Categories</h1>
<table>
	<thead>
		<tr>
			<th>id</th>
			<th>name</th>
			<th>code</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="category" items="${requestScope.categories}">
		<tr>
			<td>${category.id}</td>
			<td>${category.name}</td>
			<td>${category.code}</td>
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
			<td><input type="text" name="ncn"></td>
		</tr>
		<tr>
			<td>Code:</td>
			<td><input type="text" name="ncc"></td>
		</tr>
	</table>
	<input type="submit" value="Send">
</form>
</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Stores</title>
</head>
<body>
<h1>Stores</h1>
<table>
	<thead>
		<tr>
			<th>id</th>
			<th>name</th>
			<th>city</th>
			<th>street</th>
			<th>number</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="store" items="${requestScope.stores}">
		<tr>
			<td>${store.id}</td>
			<td>${store.name}</td>
			<td>${store.city}</td>
			<td>${store.street}</td>
			<td>${store.number}</td>
			<td>
				<form method="post" action="">
					<input type="hidden" name="ds" value="${store.id}">
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
			<td><input type="text" name="nsn"></td>
		</tr>
		<tr>
			<td>City:</td>
			<td><input type="text" name="nsc"></td>
		</tr>
		<tr>
			<td>Street:</td>
			<td><input type="text" name="nss"></td>
		</tr>
		<tr>
			<td>Number:</td>
			<td><input type="text" name="nsb"></td>
		</tr>
	</table>
	<input type="submit" value="Send">
</form>
</body>
</html>
</html>
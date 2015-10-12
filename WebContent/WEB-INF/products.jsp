<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Products</title>
</head>
<body>
<h1>Products</h1>
<table>
	<thead>
		<tr>
			<th>id</th>
			<th>category id</th>
			<th>name</th>
			<th>int quantity</th>
			<th>part quantity</th>
			<th>unit id</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="product" items="${requestScope.products}">
		<tr>
			<td>${product.id}</td>
			<td>${product.categoryId}</td>
			<td>${product.name}</td>
			<td>${product.intQuantity}</td>
			<td>${product.partQuantityNumerator}</td>
			<td>${product.unitId}</td>
			<td>
				<form method="post" action="">
					<input type="hidden" name="dp" value="${product.id}">
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
<!--  -->
<form method="POST" action="">
	<table>
		<tr>
			<td>Category:</td>
			<td><select name="npci">
				<c:forEach var="category" items="${requestScope.categories}">
				<option value="${category.id}">${category.code}</option>
				</c:forEach>
			</select>
			</td>
		</tr>
		<tr>
			<td>Name:</td>
			<td><input type="text" name="npn"></td>
		</tr>
		<tr>
			<td>Quantity:</td>
			<td><input type="number" name="npq" min="0" step="0.01"></td>
		</tr>
		<tr>
			<td>Unit:</td>
			<td><select name="npu">
				<c:forEach var="unit" items="${requestScope.units}">
					<option value="${unit.id}">${unit.code}</option>
				</c:forEach>
				</select>
			</td>
		</tr>
	</table>
	<input type="submit" value="Send">
</form>

</body>
</html>
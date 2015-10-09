<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Purchases</title>
</head>
<body>
<h1>Purchases</h1>
<table>
	<thead>
		<tr>
			<th>id</th>
			<th>productId</th>
			<th>storeId</th>
			<th>price</th>
			<th>comment</th>
			<th>date</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="purchase" items="${requestScope.purchases}">
		<tr>
			<td>${purchase.id}</td>
			<td>${purchase.productId}</td>
			<td>${purchase.storeId}</td>
			<td>${purchase.intPrice}.${purchase.centPrice}</td>
			<td>${purchase.comment}</td>
			<td>${purchase.date}</td>
			<td>
				<form method="post" action="">
					<input type="hidden" name="dp" value="${purchase.id}">
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
			<td>Product:</td>
			<td><select name="np">
				<c:forEach var="product" items="${requestScope.products}">
					<option value="${product.id}">${product.name}</option>
				</c:forEach>
			</select>
			</td>
		</tr>
		<tr>
			<td>Store:</td>
			<td><select name="nps">
				<c:forEach var="store" items="${requestScope.stores}">
					<option value="${store.id}">${store.name}</option>
				</c:forEach>
			</select>
			</td>
		</tr>
		<tr>
			<td>Price:</td>
			<td><input type="number" min="0.00" step=0.01 name="npp"></td>
		</tr>
		<tr>
			<td>Comment:</td>
			<td><input type="text" name="npc"></td>
		</tr>
		<tr>
			<td>Date:</td>
			<td><input type="date" name="npd"></td>
		</tr>
	</table>
	<input type="submit" value="Send">
</form>
</body>
</html>
</html>
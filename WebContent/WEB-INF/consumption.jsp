<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Consumption</title>
</head>
<body>
<h1>Consumption</h1>
<table>
	<thead>
		<tr>
			<th>id</th>
			<th>purchaseId</th>
			<th>consumed part</th>
			<th>price</th>
			<th>comment</th>
			<th>date</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="consumption" items="${requestScope.consumption}">
		<tr>
			<td>${consumption.id}</td>
			<td>${consumption.purchaseId}</td>
			<td>${consumption.consumedNumerator}/${consumption.consumedDenominator}</td>
			<td>${consumption.comment}</td>
			<td>${consumption.date}</td>
			<td>
				<form method="post" action="">
					<input type="hidden" name="dc" value="${consumption.id}">
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
			<td>Purchase:</td>
			<td><select name="ncp">
				<c:forEach var="purchase" items="${requestScope.purchases}">
					<option value="${purchase.id}">${purchase.comment}</option>
				</c:forEach>
			</select>
			</td>
		</tr>
		<tr>
			<td>Consumed part:</td>
			<td><input type="number" min="1" step="1" name="ncnum"></td>
			<td>/</td>
			<td><input type="number" min="1" step="1" name="ncden"></td>
		</tr>
		<tr>
			<td>Comment:</td>
			<td><input type="text" name="ncc"></td>
		</tr>
		<tr>
			<td>Date:</td>
			<td><input type="date" name="ncd"></td>
		</tr>
	</table>
	<input type="submit" value="Send">
</form>
</body>
</html>
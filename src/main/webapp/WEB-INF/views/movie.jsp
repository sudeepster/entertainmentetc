<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Available near you</title>
</head>
<body>
		<h1><i><c:out value="${movieTitle}" /></i> Available Near You</h1>
		
		<!-- <h3>Nearby store:</h3>
		<c:out value="${store.name}" /><br />
		<c:out value="${store.address}" /><br />
		<c:out value="${store.city}" /> <c:out value="${store.region}" /> <c:out value="${store.postalCode}"  />
		
		<table>
			<tr>
				<th>Name</th>
				<th>Price</th>
				<th>SKU</th>
			</tr>
			<c:forEach items="${products}" var="product">
				<tr>
					<td><c:out value="${product.name}" /></td>
					<td><c:out value="${product.salePrice}" /></td>
					<td><c:out value="${product.sku}" /></td>
				</tr>		
			</c:forEach>
		</table> -->
		<table>
			<tr>
				<th>Name</th>
				<th>Price</th>
				<th>Store</th>
				<th>Store Address</th>
			</tr>
			<c:forEach items="${productStores}" var="productStore">
				<tr>
					<td><c:out value="${productStore.product.name}"/></td>
					<td><c:out value="${productStore.product.salePrice}"/></td>
					<td><c:out value="${productStore.store.name}"/></td>
					<td><c:out value="${productStore.store.address}"/></td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>
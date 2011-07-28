<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Home</title>
	</head>
	<body>
	<ul>
		<li><a href="<c:url value="/signout" />">Sign Out</a></li>
	</ul>
	
	<!-- <h3><c:out value="${movieName}" /> Available Near You</h3>
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
	
	<h3>You are here: <c:out value="${location}" /></h3>
	
	
	<h3>Your Favorite Movies and TV Shows</h3>
	Click on a title to find it near you:
 	<!-- <ul>
	<c:forEach items="${music}" var="music">
		<li><img src="http://graph.facebook.com/<c:out value="${music.id}"/>/picture" align="middle"/><c:out value="${music.name}"/></li>
	</c:forEach>
	</ul>
	<ul>
	<c:forEach items="${books}" var="books">
		<li><img src="http://graph.facebook.com/<c:out value="${books.id}"/>/picture" align="middle"/><c:out value="${books.name}"/></li>
	</c:forEach>
	</ul> -->
	<ul>
	<c:forEach items="${movies}" var="movies">
		<li><img src="http://graph.facebook.com/<c:out value="${movies.id}"/>/picture" align="middle"/><a href="/movie?movieTitle=${movies.name}&category=cat02015"><c:out value="${movies.name}"/></a></li>
	</c:forEach>
	</ul>
	<ul>
	<c:forEach items="${television}" var="television">
		<li><img src="http://graph.facebook.com/<c:out value="${television.id}"/>/picture" align="middle"/><a href="/movie?movieTitle=${television.name}&category=cat02015"><c:out value="${television.name}"/></a></li>
	</c:forEach>
	</ul>

	</body>
</html>
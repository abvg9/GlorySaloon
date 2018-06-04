<%@ include file="../jspf/header.jspf"%>

<strong>Ranking</strong>
<c:forEach items="${ranking}" var="r">
	<tr>
		<td><img src="${imagen}${r.id}" /></td>
		<td>${r.login}</td>
		<td>${r.dinero}</td>
		<td>${r.nacion}</td>
	</tr>
</c:forEach>

<div>
	<strong>Ver ranking</strong>
	<form action="/user/verRanking" method="get" id = "verRanking">
		<select name="busqueda">
			<option value="amigos">Por amigos</option>
			<option value="pais">Por pais</option>
			<option value="global">Global</option>
		</select> 
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<button type="submit" class="btn" form="verRanking" value="Submit">Ver ranking</button>
	</form>
</div>
<%@ include file="../jspf/footer.jspf"%>

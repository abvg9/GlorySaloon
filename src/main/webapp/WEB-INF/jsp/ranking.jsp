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

<strong>Ver ranking</strong>
<form action="/user/verRanking" method="get">
	<select name="busqueda">
		<option value="amigos">Por amigos</option>
		<option value="pais">Por pais</option>
		<option value="global">Global</option>
	</select> <input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
	<div class="form-actions">
		<button type="submit" class="btn">Ver ranking</button>
	</div>
</form>
<%@ include file="../jspf/footer.jspf"%>

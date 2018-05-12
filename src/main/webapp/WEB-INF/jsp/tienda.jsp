<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ include file="../jspf/header.jspf"%>

<div class="starter-template">
	<h1>Bienvenido a la tienda!</h1>

	<strong>Items</strong>
	<c:forEach items="${tienda}" var="i">
		<tr>
			<td>${i.nombre}</td>
			<td>${i.descripcion}</td>
			<td>${i.id}</td>
			<td>${i.precio}</td>
		</tr>
	</c:forEach>

	<c:if test="${not empty mensaje}">
		<strong>${mensaje}</strong>
	</c:if>

	<strong>Comprar item.</strong>
	<form action="/user/comprarItem" method="post">
		<label for="id">Id del item<input name="id_it" /></label> <input
		type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="form-actions">
			<button type="submit" class="btn">Comprar item</button>
		</div>
	</form>
	
	<c:if test="${fn:contains(user.roles, 'ADMIN')}">
		<strong>Borrar item.</strong>
		<form action="/admin/borrarItem" method="post">
			<label for="id">Id del item<input name="id" /></label> <input
			type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div class="form-actions">
				<button type="submit" class="btn">Borrar item</button>
			</div>
		</form>
		
		<strong>Crear item.</strong>
		<form action="/admin/añadirItem" method="post">
			<label for="nombre">Nombre<input name="nombre" /></label>
			<label for="precio">Precio<input name="precio" /></label> 
			<label for="descripcion">Descripcion<input name="descripcion" /></label>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div class="form-actions">
				<button type="submit" class="btn">Crear item</button>
			</div>
		</form>
	</c:if>

</div>

<%@ include file="../jspf/footer.jspf"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ include file="../jspf/header.jspf"%>

<c:if test="${empty tema}">
	<h1>Bienvenido al foro</h1>
</c:if>

<c:if test="${not empty mensaje}">
	<strong>${mensaje}</strong>
</c:if>

<c:if test="${not empty tema}">
	<h1>Tema: ${tema}</h1>
</c:if>

<c:if test="${not empty tema and tema == foro[0].tema}">
	
	
	<c:forEach items="${foro}" var="f">
	<tr>
		<td>${f.usuario.login}</td>
		<td>${f.fecha}</td>
		<td>${f.comentario}</td>
		
	</tr>
	<form action="/user/borrarComentario" method="post">
		<c:if test="${f.usuario.id == user.id or fn:contains(user.roles, 'ADMIN')}">
			<input hidden="submit" name="id_c" value="${f.id}" />
			<input hidden="submit" name="tema" value="${f.tema}" />
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="form-actions">
				<button type="submit" class="btn">Borrar comentario</button>
			</div>
		</c:if>
	</form>
	</c:forEach>
</c:if>

<c:if test="${not empty tema}">
	<strong>Anade un comentario.</strong>
	<form action="/user/comentar" method="post">
		<label for="comentario">Comentario<input name="comentario" /></label>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input hidden="hidden" name="tema" value="${tema}" />
		<div class="form-actions">
			<button type="submit" class="btn">Comentar</button>
		</div>
	</form>
</c:if>

<strong>Introduce el tema que quieres abrir.</strong>
<form action="/user/foro" method="get">
	<select name="tema">
		<option value="Actualidad">Actualidad</option>
		<option value="General">General</option>
		<option value="Noobs">Noobs</option>
		<option value="Noticias">Noticias</option>
		<option value="Pros">Pros</option>
	</select> 
	<input hidden="hidden" name="tema" value="${tema}" />	
	<div class="form-actions">
		<button type="submit" class="btn">Ver tema</button>
	</div>
</form>



<%@ include file="../jspf/footer.jspf"%>

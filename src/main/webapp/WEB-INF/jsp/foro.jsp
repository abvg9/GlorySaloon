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
	<div>
		<form action="/user/borrarComentario" method="post" id = "borrarComentario">
			<c:if test="${f.usuario.id == user.id or fn:contains(user.roles, 'ADMIN')}">
				<input hidden="submit" name="id_c" value="${f.id}" />
				<input hidden="submit" name="tema" value="${f.tema}" />
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<button type="submit" class="btn" form="borrarComentario" value="Submit">Borrar comentario</button>
			</c:if>
		</form>
	</div>
	</c:forEach>
</c:if>

<c:if test="${not empty tema}">
	<strong>Anade un comentario.</strong>
	<div>
		<form action="/user/comentar" method="post" id = "comentar">
			<label for="comentario">Comentario<input name="comentario" /></label>	
			<input hidden="hidden" name="tema" value="${tema}" />
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button type="submit" class="btn" form="comentar" value="Submit">Comentar</button>
		</form>
	</div>
</c:if>

<strong>Introduce el tema que quieres abrir.</strong>
<div>
	<form action="/user/foro" method="get" id = "foro">
		<select name="tema">
			<option value="Actualidad">Actualidad</option>
			<option value="General">General</option>
			<option value="Noobs">Noobs</option>
			<option value="Noticias">Noticias</option>
			<option value="Pros">Pros</option>
		</select> 
		<input hidden="hidden" name="tema" value="${tema}" />	
		<button type="submit" class="btn" form="foro" value="Submit">Ver tema</button>
	</form>
</div>


<%@ include file="../jspf/footer.jspf"%>

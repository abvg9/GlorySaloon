<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@ include file="../jspf/header.jspf"%>

<div class="starter-template">

	<c:if test="${not empty mensaje}">
		<strong>${mensaje}</strong>
	</c:if>

	<c:choose>
		<c:when test="${empty user}">
			<h1>¡Inicia sesión!</h1>
			<br />
			<form action="/login" method="post">
				<fieldset>
					<div class="rightAl">
						<label for="username">Usuario</label> <input type="text" id="username" name="username" /><br>
						<br> <label for="password">Contrasena</label> <input type="password" id="password" name="password" />
					</div>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> <br>
					<div class="form-actions">
						<button type="submit" 
								class="btn rightAlBtn">
								Entrar
						</button>
					</div>
				</fieldset>
				
				<div>
					¿Todavía no estás registrado? Regístrate <a href="/crearCuenta">aquí</a>
				</div>
			</form>
			<br>
			<br>
			<br>
			<br>
			<br>
		</c:when>
		<c:otherwise>
			<h1>¡Bienvenido ${user.login}!</h1>
		</c:otherwise>
	</c:choose>
	<br>
	<br>
	<br>
</div>

<%@ include file="../jspf/footer.jspf"%>

<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ include file="../jspf/header.jspf"%>

<div class="container">
	<c:if test="${not empty mensaje }">
		<div class="alert alert-info" role="alert">
			<strong>${mensaje} ${classMensaje}</strong>
		</div>
		<br>
	</c:if>
	<form role="form" action="/user/modificarPerfil" method="post"
		enctype="multipart/form-data">
		<div class="form-group row">
			<label class="col-lg-3 col-form-label form-control-label"
				for="nombre">Nombre</label>

			<div class="col-lg-9">
				<input class="form-control" type="text" name="nombre"
					value="${user.login}">
			</div>
		</div>

		<div class="form-group row">
			<label for="email" class="col-lg-3 col-form-label form-control-label">Email</label>
			<div class="col-lg-9">
				<input class="form-control" name="email" type="email"
					value="${user.email}">
			</div>
		</div>

		<div class="form-group row">
			<label class="col-lg-3 col-form-label form-control-label">Pais</label>
			<div class="col-lg-9">
				<select name="nacion" class="form-control" size="0">
					<option value="Espana" selected="selected">Espana</option>
					<option value="Francia">Francia</option>
					<option value="Italia">Italia</option>
					<option value="Marruecos">Marruecos</option>
					<option value="Portugal">Portugal</option>
				</select>
			</div>
		</div>

		<div class="form-group row">
			<label for="email" class="col-lg-3 col-form-label form-control-label">Contraseña
				nueva</label>
			<div class="col-lg-9">
				<input class="form-control" name="contNueva" type="password">
			</div>
		</div>

		<div class="form-group row">
			<label class="col-lg-3 col-form-label form-control-label">Contraseña
				actual</label>
			<div class="col-lg-9">
				<input name="contActual" class="form-control" type="password">
			</div>
		</div>
		<div class="form-group row">
			<label class="col-lg-3 col-form-label form-control-label"></label>
			<div class="col-lg-9">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}"> <input type="reset"
					class="btn btn-secondary" value="Cancelar"> <input
					type="submit" class="btn btn-primary" value="Guardar cambios">
			</div>
		</div>
	</form>

</div>



<%@ include file="../jspf/footer.jspf"%>

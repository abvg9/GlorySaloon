<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ include file="../jspf/header.jspf"%>

<div class="container">


	<c:if test="${fn:contains(user.roles, 'ADMIN')}">
		<h2>Eliminar Cuenta</h2>

		<form role="form" action="/admin/eliminarCuenta" method="post"
			enctype="multipart/form-data">
			<div class="form-group row">
				<label class="col-lg-3 col-form-label form-control-label"
					for="nombre">Nombre</label>

				<div class="col-lg-9">
					<input class="form-control" type="text" name="nombre">
				</div>
			</div>


			<div class="form-group row">
				<label class="col-lg-3 col-form-label form-control-label"></label>
				<div class="col-lg-9">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}"><input
						type="submit" class="btn btn-primary" value="Eliminar">
				</div>
			</div>
		</form>


		<hr>
		<h2>Crear Cuenta</h2>

		<form role="form" action="/admin/crearCuenta" method="post"
			enctype="multipart/form-data">
			<div class="form-group row">
				<label class="col-lg-3 col-form-label form-control-label"
					for="nombre">Nombre</label>

				<div class="col-lg-9">
					<input class="form-control" type="text" name="nombre">
				</div>
			</div>
			<div class="form-group row">

				<label class="col-lg-3 col-form-label form-control-label" for="cont">Contraseña</label>

				<div class="col-lg-9">
					<input class="form-control" type="password" name="cont">
				</div>
			</div>



			<div class="form-group row">
				<label for="email"
					class="col-lg-3 col-form-label form-control-label">Email</label>
				<div class="col-lg-9">
					<input class="form-control" name="email" type="email">
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
				<label for="isAdmin">is admin? </label>
				<div class="col-lg-9">
					<input type="checkbox" name="isAdmin">
				</div>
			</div>

			<div class="form-group row">
				<label class="col-lg-3 col-form-label form-control-label"></label>
				<div class="col-lg-9">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}"> <input type="reset"
						class="btn btn-secondary" value="Cancelar">  <input type="submit"
						class="btn btn-primary" value="Crear">
				</div>
			</div>
		</form>
		<hr>
		
		<strong>Crear cuenta</strong>
		<form action="/admin/crearCuenta" method="post">
			<label for="nombre">Nombre<input name="nombre"></label> <label
				for="cont">Contraseña<input type="password" name="cont"></label>
			<label for="email">email<input name="email"></label> <label
				for="isAdmin">is admin?<input type="checkbox" name="isAdmin">
			</label> <label for="nacion">nacion <select name="nacion">
					<option value="Espana">Espana</option>
					<option value="Francia">Francia</option>
					<option value="Italia">Italia</option>
					<option value="Marruecos">Marruecos</option>
					<option value="Portugal">Portugal</option>
			</select>
			</label> <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">

			<div class="form-actions">
				<button type="submit" class="btn">Crear usuario</button>
			</div>
		</form>
	</c:if>
</div>



<%@ include file="../jspf/footer.jspf"%>

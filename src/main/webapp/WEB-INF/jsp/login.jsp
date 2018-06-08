<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@ include file="../jspf/header.jspf"%>

<style>
/* Make the image fully responsive */
.carousel-inner img {
	height: 100%;
	max-height: 300px;
	width: 100%;
}

.outerView {
	border-radius: 20px;
}
</style>
<script>
	$(function() {

		$('#login-form-link').click(function(e) {
			$("#login-form").delay(100).fadeIn(100);
			$("#register-form").fadeOut(100);
			$('#register-form-link').removeClass('active');
			$(this).addClass('active');
			e.preventDefault();
		});
		$('#register-form-link').click(function(e) {
			$("#register-form").delay(100).fadeIn(100);
			$("#login-form").fadeOut(100);
			$('#login-form-link').removeClass('active');
			$(this).addClass('active');
			e.preventDefault();
		});

	});
	$(function() {
		$('.carousel').carousel({
			interval : 3000
		});
	});
</script>

<div class="container">
	<c:if test="${not empty mensaje }">
		<div class="alert alert-info" role="alert">
			<strong>${mensaje} ${classMensaje}</strong>
		</div>
		<br>
	</c:if>
	<c:choose>
		<c:when test="${empty user}">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<div class="panel panel-login">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-6">
									<a href="#" class="active" id="login-form-link">Iniciar
										sesión</a>
								</div>
								<div class="col-xs-6">
									<a href="#" id="register-form-link">Regístrate ahora</a>
								</div>
							</div>
							<hr>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<form id="login-form" action="/login" method="post" role="form"
										style="display: block;">
										<div class="form-group">
											<input type="text" name="username" id="username" tabindex="1"
												class="form-control" placeholder="Usuario" value="">
										</div>
										<div class="form-group">
											<input type="password" name="password" id="password"
												tabindex="2" class="form-control" placeholder="Contraseña">
										</div>
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<div class="form-group">
											<div class="row">
												<div class="col-sm-6 col-sm-offset-3">
													<input type="submit" name="login-submit" id="login-submit"
														tabindex="4"
														class="form-control btn btn-login comboBoxHeight"
														value="Iniciar sesión">
												</div>
											</div>
										</div>

									</form>
									<form id="register-form" action="/user/crearCuenta"
										method="post" role="form" style="display: none;">
										<div class="form-group">
											<input type="text" name="nombre" id="usuario" tabindex="1"
												class="form-control" placeholder="Usuario" value="">
										</div>
										<div class="form-group">
											<input type="email" name="email" id="email" tabindex="1"
												class="form-control" placeholder="Correo electronico"
												value="">
										</div>
										<div class="form-group">
											<input type="password" name="cont" id="password" tabindex="2"
												class="form-control" placeholder="Contraseña">
										</div>
										<div class="form-group">
											<select name="nacion" tabindex="2"
												class="form-control comboBoxHeight">
												<option value="Espana">Espana</option>
												<option value="Francia">Francia</option>
												<option value="Italia">Italia</option>
												<option value="Marruecos">Marruecos</option>
												<option value="Portugal">Portugal</option>
											</select>
										</div>
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<div class="form-group">
											<div class="row">
												<div class="col-sm-6 col-sm-offset-3">
													<input type="submit" name="register-submit"
														id="register-submit" tabindex="4"
														class="form-control btn btn-register comboBoxHeight"
														value="Crear cuenta">
												</div>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:when>
	</c:choose>
</div>
<%@ include file="../jspf/footer.jspf"%>
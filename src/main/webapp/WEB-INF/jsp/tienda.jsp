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
	</c:if>
	<div class="row">

		<br>

		<div class="row">
			<c:if test="${fn:contains(user.roles, 'ADMIN')}">
				<div class="col-lg-4 col-md-6 mb-4">
					<form action="/admin/anadirItem" method="post">
						<div class="card h-100">
							<div class="card-body">
								<label for="nombre"><input placeholder="Nombre"
									name="nombre" /></label> <label for="precio"><input
									placeholder="Precio" name="precio" /></label> <label
									for="descripcion"><input placeholder="Descripcion" name="descripcion" /></label>

							</div>
							<div class="card-footer">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<div class="form-actions">
									<button type="submit" class="btn">Crear item</button>
								</div>
							</div>
						</div>
					</form>
				</div>

			</c:if>
			<c:forEach items="${tienda}" var="i">

				<div class="col-lg-4 col-md-6 mb-4">
					<div class="card h-100">
						
						<div class="card-body">
							<h4 class="card-title">${i.nombre}</h4>
							<h5>${i.precio}</h5>
							<p class="card-text">${i.descripcion}</p>
						</div>
						<div class="card-footer">
							<div class="botonCompraBorrar">
								<form action="/user/comprarItem" method="post">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
									<button type="submit" name="id_it" value="${i.id}" class="btn">Comprar
										item</button>
								</form>

							</div>
							<div class="botonCompraBorrar">
								<c:if test="${fn:contains(user.roles, 'ADMIN')}">
									<form action="/admin/borrarItem" method="post">
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<button type="submit" name="id" value="${i.id}" class="btn">Eliminar
											item</button>
									</form>

								</c:if>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<!-- /.row -->
	</div>
	<!-- /.row -->

</div>
<!-- /.container -->



<%@ include file="../jspf/footer.jspf"%>
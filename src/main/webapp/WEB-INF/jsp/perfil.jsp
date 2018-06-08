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
	<div class="row my-2">
		<div class="col-lg-8 order-lg-2">
			<ul class="nav nav-tabs">
				<li class="nav-item"><a href="" data-target="#profile"
					data-toggle="tab" class="nav-link active">Perfil</a></li>
				<li class="nav-item"><a href="" data-target="#amigos"
					data-toggle="tab" class="nav-link">Amigos</a></li>
				<li class="nav-item"><a href="" data-target="#items"
					data-toggle="tab" class="nav-link">Inventario</a></li>
			</ul>
			<div class="tab-content py-4">
				<div class="tab-pane active" id="profile">
					<div class="row">
						<div class="col-md-12">
							<br>
							<div style="font-size: 1.1em;">
								<p>
									<strong>Email: </strong>${user.email}</p>
								<br>
								<p>
									<strong>País: </strong>${user.nacion}</p>
							</div>
							<br> <br>
							<h4 style="font-size: 1.5em;">Estadísticas</h4>
							<hr>
							<div style="font-size: 1.1em; line-height: 30px;">
								<p>
									<strong>Partidas Jugadas: </strong>${user.pjugadas}</p>
								<p>
									<strong>Partidas ganadas: </strong>${user.pganadas}</p>

								<p>
									<strong>Partidas perdidas: </strong>${user.pperdidas}</p>

								<p style="color: green;">
									<strong>Dinero total ganado: </strong>$${user.dganado}
								</p>

								<p style="color: red;">
									<strong>Dinero total perdido: </strong>$${user.dperdido}
								</p>

							</div>

						</div>
					</div>
					<!--/row-->

				</div>
				<div class="tab-pane" id="amigos">

					<div class="container bootstrap snippet">

						<div class="header">
							<h3 class="text-muted prj-name">
								<span class="fa fa-users fa-2x principal-title"></span> Amigos
							</h3>

						</div>

						<div class="jumbotron list-content">
							<ul class="list-group">
								<li href="#" class="list-group-item title">Lista amigos</li>


								<li href="#" class="list-group-item text-left"><form
										action="/user/anadirAmigo" method="post">
										<label for="nombre"><input placeholder="Nombre"
											name="nombreA" /></label> <input type="hidden"
											name="${_csrf.parameterName}" value="${_csrf.token}" />
										<div class="form-actions">
											<button type="submit" class="btn">
												<strong>Anadir amigo</strong>
											</button>
										</div>
									</form>
									<div class="break"></div></li>



								<c:forEach items="${user.amigos}" var="u">
									<li href="#" class="list-group-item text-left"><img
										class="img-thumbnail" src="${imagen}${u.id}"> <label
										class="name"> ${u.login} <br>
									</label> <label class="pull-right">
											<form action="/user/eliminarAmigo" method="post">

												<input type="hidden" name="${_csrf.parameterName}"
													value="${_csrf.token}" />
												<button type="submit" name="id" value="${u.id}"
													class="personalizedButton btn btn-danger  btn-sm glyphicon glyphicon glyphicon-trash">&nbsp;&nbsp;Eliminar
													amigo</button>

											</form>

											<form action="/user/perfilAmigo" method="get">
												<input type="hidden" name="${_csrf.parameterName}"
													value="${_csrf.token}" />
												<button type="submit" name="id" value="${u.id}"
													class="personalizedButton btn btn-info  btn-sm glyphicon glyphicon-eye-open">&nbsp;&nbsp;Ver
													perfil</button>

											</form>



									</label>
										<div class="break"></div></li>
								</c:forEach>


							</ul>
						</div>
					</div>
				</div>
				<div class="tab-pane" id="items">
					<c:if test="${not empty user.propiedades}">
						<strong>Eliminar Item</strong>
						<form action="/user/borrarItem" method="post">

							<label for="id">Id del item<input name="id"></label> <input
								type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}">
							<div class="form-actions">
								<button type="submit" class="btn">Eliminar item</button>
							</div>
						</form>
						<br>
						<br>
						<div class="list-group">
							<c:forEach items="${user.propiedades}" var="i">
								<a class="list-group-item list-group-item-action">
									<h4>${i.nombre}</h4>
									<p>$${i.precio}</p>
									<p>${i.descripcion}</p>

									<div class="pull-right">${i.id}</div>
								</a>
							</c:forEach>
						</div>
					</c:if>
					<c:if test="${empty user.propiedades}">
						<div class="list-group">
							<a class="list-group-item list-group-item-action">
								<h4>¡No tienes ningún item todavía!</h4>
							</a>
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<div class="col-lg-4 order-lg-1 text-center">
			<img class="imagenPerfil mx-auto img-fluid img-circle d-block"
				src="${imagen}${user.id}" />
			<h4 class="mt-2">${user.login}</h4>
			<p>
				<strong>$${user.dinero}</strong>
			</p>
			<form method="POST" enctype="multipart/form-data"
				action="${imagen}${user.id}">
				<input type="file" name="photo"><br /> <input
					hidden="submit" name="id" value="${user.id}" /> <input
					type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="form-actions">
					<button type="submit" name="upload" value="ok">Subir foto</button>
				</div>
			</form>
		</div>
	</div>
</div>


<%@ include file="../jspf/footer.jspf"%>
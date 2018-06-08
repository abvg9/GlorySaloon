<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ include file="../jspf/header.jspf"%>
<div class="container">

	<c:if test="${not empty mensaje}">
		<strong>${mensaje}</strong>
	</c:if>

	<div class="row">
		<div class="col-3">
			<nav class="navbar-success">
				<form action="/user/foro" method="get">
					<button type="submit" value="Actualidad" name="tema"
						class="dropdown-item btn btn-success">Actualidad</button>
					<button type="submit" value="General" name="tema"
						class="dropdown-item btn btn-success">General</button>
					<button type="submit" value="Noobs" name="tema"
						class="dropdown-item btn btn-success">Noobs</button>
					<button type="submit" value="Noticias" name="tema"
						class="dropdown-item btn btn-success">Noticias</button>
					<button type="submit" value="Pros" name="tema"
						class="dropdown-item btn btn-success">Pros</button>
				</form>
			</nav>
		</div>
		<div class="col-9">
			<div class="specialContainer">
				<div class="litleMargin">
					<c:if test="${empty tema}">
						<h2>ELIGE UN TEMA</h2>
					</c:if>

					<c:if test="${not empty tema and tema == foro[0].tema}">


						<c:forEach items="${foro}" var="f">



							<div class="row">
								<div class="col-md-2">
									<img class="rounded-circle smallImg"
										src="${imagen}${f.usuario.id}">
									<p>
										<strong>${f.usuario.login}</strong><br> ${f.fecha}
									</p>
								</div>
								<div class="col-md-9">${f.comentario}</div>
								<div class="col-md-1">
									<c:if
										test="${f.usuario.id == user.id or fn:contains(user.roles, 'ADMIN')}">
										<form action="/user/borrarComentario" method="post">
											<input type="hidden" name="id_c" value="${f.id}" /> <input
												hidden="submit" name="tema" value="${f.tema}" /> <input
												type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
											<button type="submit"
												class="personalizedButton2 btn btn-danger  btn-sm glyphicon glyphicon glyphicon-trash"></button>
										</form>
									</c:if>
								</div>
							</div>
							<hr>
							<br>
						</c:forEach>
					</c:if>
				</div>
			</div>


		</div>
	</div>
	<div class="row">
		<div class="col-3"></div>
		<div class="col-9">
			<c:if test="${not empty tema}">
				<br>
				<br>
				<form action="/user/comentar" method="post">

					<div class="form-group row">
						<label for="comentario"
							class="col-lg-3 col-form-label form-control-label"> Añade
							un comentario </label>
						<div class="col-lg-7">
							<input class="form-control" name="comentario" type="text">
						</div>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" /> <input hidden="hidden" name="tema"
							value="${tema}" />
						<div class="form-actions col-lg-2">
							<button type="submit" class="btn">Comentar</button>
						</div>
					</div>
				</form>
			</c:if>
		</div>
	</div>
















</div>

<%@ include file="../jspf/footer.jspf"%>
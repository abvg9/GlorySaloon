<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@ include file="../jspf/header.jspf"%>


<div class="container">
	<div class="row">
		<div class="col-3  text-center">
			<img class="imagenPerfil mx-auto img-fluid img-circle d-block"
				src="${imagen}${perfil.id}" />
			<h4 class="mt-2">${perfil.login}</h4>
			<p>
				<strong>$${perfil.dinero}</strong>
			</p>
		</div>

		<div class="col-9">
			<br>
			<div style="font-size: 1.1em;">
				<p>
					<strong>Email: </strong>${perfil.email}</p>
				<br>
				<p>
					<strong>País: </strong>${perfil.nacion}</p>
			</div>
			<br> <br>
			<h4 style="font-size: 1.5em;">Estadísticas</h4>
			<hr>
			<div style="font-size: 1.1em; line-height: 30px;">
				<p>
					<strong>Partidas Jugadas: </strong>${perfil.pjugadas}</p>
				<p>
					<strong>Partidas ganadas: </strong>${perfil.pganadas}</p>

				<p>
					<strong>Partidas perdidas: </strong>${perfil.pperdidas}</p>

				<p style="color: green;">
					<strong>Dinero total ganado: </strong>$${perfil.dganado}
				</p>

				<p style="color: red;">
					<strong>Dinero total perdido: </strong>$${perfil.dperdido}
				</p>
			</div>
		</div>
	</div>
	<c:if test="${not empty perfil.partida}">
		<h1>Ahora mismo esta jugando. Partida: ${perfil.partida.nombre}</h1>
	</c:if>

	<c:if test="${not empty perfil.partida}">
		<strong>Amigos de ${perfil.login}</strong>
		<c:forEach items="${perfil.amigos}" var="u">
			<tr>
				<td>${u.login}</td>
			</tr>
		</c:forEach>
	</c:if>

	<c:if test="${not empty perfil.propiedades}">
		<strong>Items de ${perfil.login}</strong>
		<c:forEach items="${perfil.propiedades}" var="p">
			<tr>
				<td>${p.nombre}</td>
			</tr>
		</c:forEach>
	</c:if>

	<div>
		<a href="/perfil">Volver al perfil</a>
	</div>
</div>
<%@ include file="../jspf/footer.jspf"%>
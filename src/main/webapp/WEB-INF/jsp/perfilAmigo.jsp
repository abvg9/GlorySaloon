<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@ include file="../jspf/header.jspf"%>

	<h1>${perfil.login}</h1>
	<img src="${imagen}${perfil.id}" />
	
	<strong>Dinero</strong>
	<h1>${perfil.dinero}</h1>
	
	<strong>Email</strong>
	<h1>${perfil.email}</h1>
	
	<strong>Pais</strong>
	<h1>${perfil.nacion}</h1>
	
	<strong>Partidas ganadas</strong>
	<h1>${perfil.pganadas}</h1>
	
	<strong>Partidas perdidas</strong>
	<h1>${perfil.pperdidas}</h1>
	
	<strong>Partidas jugadas</strong>
	<h1>${perfil.pjugadas}</h1>
	
	<strong>Dinero perdido</strong>
	<h1>${perfil.dperdido}</h1>	
	
	<strong>Dinero ganado</strong>
	<h1>${perfil.dganado}</h1>
	
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

<%@ include file="../jspf/footer.jspf" %>
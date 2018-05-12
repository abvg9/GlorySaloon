<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ include file="../jspf/header.jspf" %>

<h1>Bienvenido al saloon</h1>


<c:if test="${not empty mensaje}">
	<strong>${mensaje}</strong>
</c:if>

<c:if test="${not empty saloon and empty user.partida}">
	<c:forEach items="${saloon}" var="p">
		Nombre<td>${p.nombre}</td>Juego<td>${p.juego}</td>
		<td>${p.jugadores.size}/${p.MaxJugadores}</td>
		
		Jugadores
		<c:forEach items="${p.jugadores}" var="j">
			<td>${j.login}</td>
		</c:forEach>
		
		<c:choose>
		    <c:when test="${p.pass == 'no'}">
		        Sin contraseña 
		    </c:when>    
		    <c:otherwise>
		        Con contraseña	        
		        <label for="pass">Contraseña <input type="password" id="pass" /> </label>
		    </c:otherwise>
		</c:choose>
		
		<c:choose>
		    <c:when test="${p.abierta == true}">
		        Abierta 
		        <form action="/user/unirsePartida" method="post">
		        	<input hidden="submit" name="id_p" value="${p.id}" />
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<div class="form-actions">
						<button type="submit" class="btn">Unirse</button>
					</div>
				</form>
		    </c:when>    
		    <c:otherwise>
		        Cerrada 
		    </c:otherwise>
		</c:choose>
		
	</c:forEach>
</c:if>

<c:if test="${not empty user.partida}">
<form action="/user/empezarPartida" method="post">
	<input hidden="submit" name="id_p" value="${user.partida.id}" />
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<div class="form-actions">
		<button type="submit" class="btn">Empezar partida</button>
	</div>
</form>
</c:if>

<c:if test="${empty user.partida}">
	<strong>Crear partida</strong>
	<form action="/user/crearPartida" method="post">
	
		<label for="nombrePar">Nombre<input name="nombrePar" /></label>
		Contraseña(si la dejas en blaco, todo el mundo podra entrar)<label for="cont" > <input type="password" name="cont" /></label>
		<label for="maxJugadores">Maximo de jugadores<input name="maxJugadores" /></label>
		<select name="juego">
			<option value="BlackJack">BlackJack</option>
			<option value="Poker">Poker</option>
		</select>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="form-actions">
			<button type="submit" class="btn">Crear partida</button>
		</div>
	</form>


	<strong>Buscar partidas</strong>
	<form action="/user/verSaloon" method="get">
	
		Si no rellenas ningun campo, mostrara las partidas en las que estan tus amigos.
		Buscara por el campo que rellenes mas arriba. El de abajo, si lo hay, lo ignorará.
		<label for="nombrePar">Nombre<input name="nombrePar" /></label>
		<select name="juego">
			<option value="BlackJack">BlackJack</option>
			<option value="Pocker">Pocker</option>
			<option value="Amigos">Amigos</option>
		</select>
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="form-actions">
			<button type="submit" class="btn">Buscar partidas</button>
		</div>
	</form>
</c:if>

<%@ include file="../jspf/footer.jspf" %>

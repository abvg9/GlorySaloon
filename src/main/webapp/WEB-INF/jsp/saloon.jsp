<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ include file="../jspf/header.jspf" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<h1>Bienvenido al saloon</h1>

<c:if test="${not empty mensaje}">
	<strong>${mensaje}</strong>
</c:if>

<c:if test="${not empty saloon and empty user.partida}">
	<c:forEach items="${saloon}" var="p">
		Nombre<td>${p.nombre}</td>Juego<td>${p.juego}</td>
		<td>${fn:length(p.jugadores)}/${p.maxJugadores}</td>
		
		Jugadores
		<c:forEach items="${p.jugadores}" var="j">
			<td>${j.login}</td>
		</c:forEach>
			
		<c:choose>
		    <c:when test="${p.abierta == true}">
		        Abierta 
		        <form action="/user/unirsePartida" method="post">
		        	<input hidden="submit" name="id_p" value="${p.id}" />
		        	<c:choose>
					    <c:when test="${p.pass == 'no'}">
					        Sin contraseña 
					    </c:when>    
					    <c:otherwise>
					        Con contraseña	        
					        <label for="pass">Contraseña <input type="password" name="pass" /> </label>
					    </c:otherwise>
					</c:choose>
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
	
	<script>
	window.onload = function (){
		setInterval(function() {
			document.forms["formEmpezar"].submit();
		}, 5000); 
	}
	</script>
	
	<form id= "formEmpezar" action="/user/empezarPartida" method="post">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	
	<form action="/user/salirDeLaPartida" method="post">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="form-actions">
			<button type="submit" class="btn">Salir de la partida</button>
		</div>
	</form>
</c:if>

<c:if test="${empty user.partida}">

	<script>
	window.onload = function() {
		// code in here will only be executed when page fully loaded
		console.log("entered into chat");
		
		var socket = new WebSocket("${endpoint}");
		$("#escrito").submit(function (e) {
			var t = $("#texto").val();
			socket.send(t);
			$("#texto").val("");
			e.preventDefault(); // avoid actual submit
		});
		socket.onmessage = function(e) {
			var ta = $("#recibido");
			ta.val(ta.val() + '\n' + e.data);
		}
	}
	</script>


	<h1>Chat del saloon</h1>
	
	<textarea id="recibido" cols="80" rows="10">
	</textarea>
	<form id="escrito">
		<input id="texto" size="80" placeholder="escribe algo y pulsa enter para enviarlo"/>
	</form>


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
	
		Buscara por el campo que rellenes mas arriba. El de abajo, si esta rellenado, lo ignorará.
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

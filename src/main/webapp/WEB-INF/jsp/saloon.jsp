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
		        <form action="/user/unirsePartida" method="post" id = "unirsePartida">
		        	<input hidden="submit" name="id_p" value="${p.id}" />
		        	<c:choose>
					    <c:when test="${p.pass == 'no'}">
					        Sin contrasena 
					    </c:when>    
					    <c:otherwise>
					        Con contrasena	        
					        <label for="pass">Contrasena <input type="password" name="pass" /> </label>
					    </c:otherwise>
					</c:choose>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					
					<button type="submit" class="btn" form="unirsePartida" value="Submit">Unirse</button>
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
	
	<div>
		<form action="/user/empezarPartida" id=formEmpezar method="post"> 
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
	</div>
	
	<div>
		<form action="/user/salirDeLaPartida" method="post" id = "salirDeLaPartida">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button type="submit" class="btn" form="salirDeLaPartida" value="Submit">Salir de la partida</button>
		</form>
	</div>
</c:if>

<c:if test="${empty user.partida}">

	<script>
	window.onload = function() {
				
		var socket = new WebSocket("${endpoint}");
		$("#escrito").submit(function (e) {
			var t = $("#texto").val();
			socket.send(t);
			$("#texto").val("");
			e.preventDefault();
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
	<div>
		<form action="/user/crearPartida" method="post" id = "crearPartida">
		
			<label for="nombrePar">Nombre<input name="nombrePar" /></label>
			Contrasena(si la dejas en blaco, todo el mundo podra entrar)<label for="cont" > <input type="password" name="cont" /></label>
			<label for="maxJugadores">Maximo de jugadores<input name="maxJugadores" /></label>
			<select name="juego">
				<option value="BlackJack">BlackJack</option>
				<option value="Poker">Poker</option>
			</select>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button type="submit" class="btn" form="crearPartida" value="Submit">Crear partida</button>
		</form>
	</div>


	<strong>Buscar partidas</strong>
	<div>
		<form action="/user/verSaloon" method="get" id = "verSaloon">
		
			Buscara por el campo que rellenes mas arriba. El de abajo, si esta rellenado, lo ignorará.
			<label for="nombrePar">Nombre<input name="nombrePar" /></label>
			<select name="juego">
				<option value="Pocker">Pocker</option>
				<option value="Amigos">Amigos</option>
			</select>
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button type="submit" class="btn" form="verSaloon" value="Submit">Buscar partidas</button>
		</form>
	</div>
	
</c:if>

<%@ include file="../jspf/footer.jspf" %>

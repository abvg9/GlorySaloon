<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../jspf/header.jspf"%>
	
<div class="starter-template">
	<h1>Partida: ${user.partida.nombre}</h1>
		
	<!-- Mostramos la partida, los jugadores y esas cositas //-->
	<c:forEach items="${user.partida.jugadores}" var="j">
		<tr>
			<td>${j.login}</td>
			<td>${j.dinero}</td>
		</tr>
	</c:forEach>

	<textarea id="recibido" cols="80" rows="10"></textarea>
	
	<form id="escrito">
		<input id="texto" size="80" placeholder="escribe algo y pulsa enter para enviarlo"/>
	</form>
	
	<form id="pasarTurno">
		<div class="form-actions">
		    <button type="submit"
		    		class ="btn"
				   	name="apostar"
				   	onclick="pasarTurno();"
				   	<c:if test="${aposto == true and turno == true}">
				    	<c:out value="disabled='disabled'"/>
				   	</c:if>>
			 Pasar turno</button>	
		</div>
	</form>
	
	<form id="pedirCarta">
		<div class="form-actions">
		    <button type="submit"
		    		class ="btn"
				   	name="pediCarta"
				   	onclick="pedirCarta();"
				   	<c:if test="${aposto == true and turno == true}">
				    	<c:out value="disabled='disabled'"/>
				   	</c:if>>
			 Pedir carta</button>	
		</div>
	</form>
	
	<form id="salir">
		<div class="form-actions">
		    <button type="submit"
		    		class ="btn"
				   	name="salir"
				   	onclick="salir();">
			 Salir</button>	
		</div>
	</form>
	
	<form id="apostar">
		<div class="form-actions">
			<label for="apostado">Apuesta<input id= "apostado" name="apostado" /></label>
		    <button type="submit"
		    		class ="btn"
				   	name="apostar"
				   	onclick="apostar();"
				   	<c:if test="${aposto == false and turno == true}">
				    	<c:out value="disabled='disabled'"/>
				   	</c:if>>
			 Apostar</button>	
		</div>
	</form>
		
</div>

<script>
window.onload = function() {
	
	socket.onmessage = function(e) {
		
		//mensaje de parte del servidor
		if(e.data.includes("server")){
			
			if(e.data.includes("turno")){
				//Es su turno
				turno = true;
			}else if(e.data.includes("acabo")){
				//La partida acabo(hay que borrar todas las imagenes de las cartas)
				cartas = new Array();
			}else{
				//si tiene mano -> nueva partida
				//si tiene carta -> pidio carta
				//formato: server (carta/mano) [palo] [valor] ...
				for(i = 0; i < e.lenght;i++){
					if(e.charAt(i) == '['){
						
						cartas.push(e.charAt(i+1));//palo
						cartas.push(e.charAt(i+5));//valor
						i+7;
					}
				}
			}
		
		//mensaje de parte de algun jugador
		}else{
			// si alguien salio, hay que actualizar la variable de session del jugador y sacarle a ese usuario de
			//${user.partida.jugadores}
			if(e.data.includes("salió")){
				
			}
			
		}
		var ta = $("#recibido");
		ta.val(ta.val() + '\n' + e.data);
	}
}
</script>

<script type="text/javascript">
	
	var turno = false;
	var aposto = false;
	var cartas = [];
	
	function setval(varval){
		aposto = varval;
	}
	
	function pasarTurno(){
		var socket = new WebSocket("${endpoint}");
		var t = "${user.login}"+" pasó de turno.";
		socket.send(t);
		e.preventDefault(); // avoid actual submit
		turno = false;
		aposto = false;
	}
	
	function pedirCarta(){
		var socket = new WebSocket("${endpoint}");
		var t = "${user.login}"+" pidió una carta.";
		socket.send(t);
		e.preventDefault(); // avoid actual submit	
	}
	
	function salir(){
		var socket = new WebSocket("${endpoint}");
		var t = "${user.login}"+" salió de la partida.";
		socket.send(t);
		window.location.href = "/saloon";
	}
	
	function apostar(){
		
		var apostado = $("#apostado")
		
		//revisar que lo que metio es un numero
		if(apostado =< 0 && apostado > ${user.dinero} && typeof apostado == 'number'){
			alert("No puedes apostar esa cantidad.");
		}else{
			var socket = new WebSocket("${endpoint}");
			var t = "${user.login}"+" apostó "+ apostado +".";
			//actualizamos la variable ${user.dinero}
			aposto = true;
			socket.send(t);
			e.preventDefault(); // avoid actual submit
			
		}
		
	}
		
</script>

<%@ include file="../jspf/footer.jspf"%>



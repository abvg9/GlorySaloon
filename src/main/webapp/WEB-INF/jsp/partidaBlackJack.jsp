<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../jspf/header.jspf"%>
<%@ page import="es.ucm.fdi.iw.model.User" %>

<h1>Partida: ${user.partida.nombre}</h1>

Movimientos
<textarea id="recibido" cols="50" rows="20" disabled></textarea>

Mano
<textarea id="cartas" cols="30" rows="6" disabled></textarea>

Jugadores
<textarea id="jugadores" cols="30" rows="6" disabled></textarea>

Total Apostado
<textarea id="bote" cols="10" rows="1" disabled></textarea>

<button type="submit"
  	class ="btn"
   	name="apostar"
   	id = btn_pasar>
	Pasar turno
</button>	

<button type="submit"
  	class ="btn"
   	name="pediCarta"
   	id = btn_pedir>
	Pedir carta
</button>	

<button type="submit"
	class ="btn"
 	name="salir"
 	id = btn_salir>
 	Salir
</button>	

<label for="apostado">Apuesta<input id= "apostado" name="apostado" /></label>
<button type="submit"
	class ="btn"
	name="apostar"
	id = btn_apostar>
	Apostar
</button>	

<script type="text/javascript">
window.onload = function() {
	
	//VARIABLES GLOBALES
	
	//=>botones
	var btn_pasar = $("#btn_pasar");
	var btn_pedir = $("#btn_pedir");
	var btn_salir = $("#btn_salir");
	var btn_apostar = $("#btn_apostar");
	
	btn_pasar.prop("disabled",true);
	btn_pedir.prop("disabled",true);
	btn_salir.prop("disabled",true);
	btn_apostar.prop("disabled",true);
	
	//=>utilidades
	var socket = new WebSocket("${endpoint}");
	var bote = $("#bote");
	bote.val("0");
	var jugadores = $("#jugadores");
	var apostado = $("#apostado");
	var mensaje = $("#recibido");
	
	//=>jugadores
	var jug = new Array();
	cargaJugadores();
	
	//=>dinero
	var dinero = ${user.dinero};

	socket.onmessage = function(e) {
		
		let length;
		let mens = e.data.split(" ");
		let cart;
		
		switch (mens[0]) {
		
	    	case "turno": 
	    		alert("Es tu turno.");
	    		btn_apostar.prop("disabled",false);
	    		break;
	    		
	    	case "acabo": 
	    		let ganadores = "";
	    		bote.val("0");
	    		mostrarMensaje("La partida ha acabado");
	    		
	    		//formato
	    		//nombrejug, cartas, dinero , ... , numGnadores

				let index = mens.length;
	    		length = mens[index-1];
	    		length = parseFloat(length);
	    		if(length == 1){
	    			mostrarMensaje("El ganador es:");
	    		}else if(length != jug.length){
	    			mostrarMensaje("Los ganadores son:");
	    		}else{
	    			mostrarMensaje("Habeis empatado:");
	    		}
	    		let j = 1;
	    		debugger;
				for(let i = 0; i < length;i++){
					mostrarMensaje(mens[j] + " " + mens[j+1]);
					actualizaJugador(mens[j],+mens[j+2]);
					j = j+3;
				}

	    		btn_salir.prop("disabled",false);
	    		break;
	    	case "mano":
				length = mens.length;
				var cartas = $("#cartas");
				cartas.val("");
				cart = new Array();
				for(let i = 1; i < length-1;i++){
					cart.push(mens[i]);
					cartas.val(cartas.val() + '\n' + cart[i-1]);
				}
		        break;
		    
	    	case "salio":
		    	mostrarMensaje(mens[1] + " salió de la partida");	
		    	actualizaJugador(mens[0],true);
		    	break;
		    	
	    	case "empezo":
	    		mostrarMensaje("La partida ha comenzado");
	    		btn_salir.prop("disabled",true);
	    		break;
	    		
	    	case "tepasaste":
	    		mostrarMensaje("Te has pasado de 21");
	    		btn_pedir.prop("disabled",true);
	    		btn_pasar.prop("disabled",true);
	    		break;   
	    		
	    	case "sinCartas":
    			mostrarMensaje("Se acabaron las cartas");
    			btn_pedir.prop("disabled",true);
    			break;
    		
	    	case "fin":
	    		mostrarMensaje("Todos los jugadores han abandonado la partida");
	    		btn_salir.prop("disabled",false);
	    		break;
	    		
	    	default:
	    		if(mens[1] == "apostó"){
	    			let bt = parseFloat(bote.val()) + parseFloat(mens[2]);
	    			bote.val(bt.toString());
	    			actualizaJugador(mens[0],-mens[2]);
	    		}
	    		mostrarMensaje(e.data);
				break;
		}
		
	}
	
	function cargaJugadores(){
		
		<% 
		User user = (User)session.getAttribute("user");
		for(int i = 0; i < user.getPartida().getJugadores().size();i++){ %>
			jug.push({
				nombre: '<%=user.getPartida().getJugadores().get(i).getLogin()%>' ,
				dinero: '<%=user.getPartida().getJugadores().get(i).getDinero()%>'
			});
	  <%}%>
	  	injectaJugadores();
	}
	
	function actualizaJugador(jugador,din){
		let i = 0;
		let length = jug.length;
		while(jug[i].nombre != jugador){
			i++;
		}
		if(!isNaN(din)){
			jug[i].dinero = parseFloat(jug[i].dinero) + din;
		}else{
			//reparar esto jug[i].pop o algo asi
			jug[i].slice(0,i-1).concat(jug[i].slice(i+1,lenght));
		}
		injectaJugadores();
	}
	
	function injectaJugadores(){
		let length = jug.length;
		jugadores.val("");
		for(let i = 0; i < length;i++){
			jugadores.val(jugadores.val() + jug[i].nombre +" " + jug[i].dinero + '\n');
		}
	}
	
	function mostrarMensaje(mens){
		mensaje.val(mensaje.val() + '\n' + mens);
	}
			
	btn_pasar.on("click", function(){
		socket.send("${user.login} pasó de turno");
		btn_pasar.prop("disabled",true);
		btn_pedir.prop("disabled",true);
	});
	
	btn_pedir.on("click", function(){
		socket.send("${user.login} pidió una carta");
	});
	
	btn_salir.on("click", function(){
		socket.send("${user.login} salió de la partida");
		window.location.href = "/saloon";
	});
	
	btn_apostar.on("click", function(){
		if(!isNaN(apostado.val())){
			
			let dinapos = parseFloat(apostado.val());
			dinapos = dinapos.toFixed(1);
			if(dinapos <= 0 || dinapos > dinero){
				alert("No puedes apostar esa cantidad");
			}else{
				dinero = dinero - dinapos;
				let bt = parseFloat(bote.val()) + parseFloat(dinapos);
    			bote.val(bt.toString());
				
				socket.send("${user.login} apostó "+ dinapos.toString());
				btn_apostar.prop("disabled",true);
				btn_pedir.prop("disabled",false);
				btn_pasar.prop("disabled",false);

				apostado.val("");
				actualizaJugador("${user.login}",-dinapos);
			}
			
		}else{
			alert("Debes introducir una cifra");
		}

	});
	
	window.onbeforeunload = function() {
		return "Si usted sale de la partida antes de que finalize la ronda será penalizado. ¿Está seguro de continuar? ";
	}
	
}
</script>

<%@ include file="../jspf/footer.jspf"%>



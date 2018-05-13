<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ include file="../jspf/header.jspf"%>
<%@ page import="es.ucm.fdi.iw.games.factorias.FactoriaJuego,
                 es.ucm.fdi.iw.games.logica.Juego,
                 es.ucm.fdi.iw.common.enums.Juegos,
                 java.util.ArrayList,
                 es.ucm.fdi.iw.games.logica.Jugador,
                 es.ucm.fdi.iw.common.utils.CargaAtributos" %>


<%!
private static class Partida {
	
  private Juego juego;

  private Partida(Juegos juego, ArrayList<Jugador> jugadores,HttpSession session){
	  this.juego = FactoriaJuego.juego(juego, jugadores,session);
  }
  
}
%>

<%! @SuppressWarnings("unchecked") %>
<%= new Partida((Juegos) request.getSession().getAttribute(CargaAtributos.juego),
		        (ArrayList<Jugador>) request.getSession().getAttribute(CargaAtributos.jugadores),
		        request.getSession()
		       ).juego.start()
%>

<strong>Partida: ${partida.nombre}</strong>

<c:if test="${not empty mensaje}">
	<strong>${mensaje}</strong>
</c:if>


<c:if test="${not empty infoPartida}">
	<strong>${infoPartida}</strong>
</c:if>

<c:if test="${not empty turno}">
	<strong>Turno de ${jugadores{turno].nombre}</strong>
</c:if>

<!-- Mesa de juego -->
<c:forEach items="${jugadores}" var="j">
	<tr>
		<td>${j.nombre}</td>
		<td>${j.dinero}</td>
	</tr>
</c:forEach>

<form action="/user/apostar" method="post">
	<label for="apostado">Apuesta<input name="apostado" /></label>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<div class="form-actions">
		<button type="submit" class="btn">Apostar</button>
	</div>
</form>

<form action="/user/pasaTurno" method="post">
	<div class="form-actions">
		<button type="submit" class="btn">Pasar turno</button>
	</div>
</form>

<form action="/user/pedirCarta" method="post">
	<div class="form-actions">
		<button type="submit" class="btn">Pedir Carta</button>
	</div>
</form>

<form action="/user/salirDeLaPartida" method="post">
	<div class="form-actions">
		<button type="submit" class="btn">Pedir Carta</button>
	</div>
</form>

<form name = "actualizarManos" action="/user/actualizarManos" method="get">
	<input hidden="submit" name="p" value= "${user.partida}" />
	<input hidden="submit" name="movimiento" value= "${manos}" />
</form>

<form name = "actualizarMovimiento" action="/user/actualizarMovimiento" method="get">
	<input hidden="submit" name="movimiento" value= "${movimiento}" />
</form>

<form name = "actualizaInfoPartida" action="/user/actualizaInfoPartida" method="get"></form>

<script>

</script>
	Actualiza();
	setTimeout('document.location.reload()',1000);
<script>

function Actualiza() {
	
	var manos = ${manos};
	var movimiento = ${movimiento}
	
	setInterval(function(){ 
							var manosCambio = document.getElementsByTagName("manos");
							var i = 0;
							var j;
							
							do{
								j = 0;
								while(manos[i].get(j).getPalo() == manosCambio[i].get(j).getPalo() && 
								 	  manos[i].get(j).getValor() == manosCambio[i].get(j).getValor()){
									  j++;
								}
								i++;
							}while(j == manos[i].size() && i < manos.length);
							
							//si alguna mano cambio, se llama a ActualizaManos
							if(i != manos.length){
								manos = manosCambio;
								ActualizaManos();
							}else if(movimiento){
								ActualizaMovimiento();
							}
							
							document.forms["actualizaInfoPartida"].submit();
							
						  }
	, 1000);
}

function ActualizaManos() {
	document.forms["actualizaManos"].submit();
}

function ActualizaMovimiento() {
	document.forms["actualizaMovimiento"].submit();
}

</script>


<%@ include file="../jspf/footer.jspf"%>



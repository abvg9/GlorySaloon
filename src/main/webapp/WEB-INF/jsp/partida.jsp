<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ include file="../jspf/header.jspf"%>
<%@ page import="es.ucm.fdi.iw.games.factoria.FactoriaJuego,
                 es.ucm.fdi.iw.games.logica.Juego,
                 es.ucm.fdi.iw.common.enums.Juegos,
                 java.util.ArrayList,
                 es.ucm.fdi.iw.games.logica.Jugador,
                 es.ucm.fdi.iw.common.utils.CargaAtributos" %>


<%!
private static class Partida {
	
  private Juego juego;

  private Partida(Juegos juego, ArrayList<Jugador> jugadores){
	  this.juego = FactoriaJuego.juego(juego, jugadores);
  }
  
}
%>

<%! @SuppressWarnings("unchecked") %>
<%= new Partida((Juegos) request.getSession().getAttribute(CargaAtributos.juego),
		        (ArrayList<Jugador>) request.getSession().getAttribute(CargaAtributos.jugadores)
		       ).juego.run()
%>


<%@ include file="../jspf/footer.jspf"%>
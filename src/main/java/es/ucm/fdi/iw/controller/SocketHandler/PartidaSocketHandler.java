package es.ucm.fdi.iw.controller.SocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import es.ucm.fdi.iw.games.logica.Juego;
import es.ucm.fdi.iw.games.logica.Jugador;
import es.ucm.fdi.iw.model.User;

public abstract class PartidaSocketHandler extends TextWebSocketHandler {
	
	private static Logger log = Logger.getLogger(PartidaSocketHandler.class);
	
	@Autowired
	protected EntityManager entityManager;
	
	protected static ArrayList<Partida> partidas = new ArrayList<Partida>();
	
	protected static class Partida{
		protected Juego juego;
		protected List<WebSocketSession> jugadoresSk;
		protected int turno;
		
		Partida(Juego juego){
			this.juego = juego;
			this.jugadoresSk = new CopyOnWriteArrayList<>();
		}
		
		protected void añadeJugador(Jugador jugador, WebSocketSession session) {
			jugador.setTurno(juego.getJugadores().size());
			juego.getJugadores().add(jugador);
			jugadoresSk.add(session);
		}
	}
	
	protected void broadcast(String message,List<WebSocketSession> jugadores) {
		log.info("broadcasting: " + message);
    	for (WebSocketSession s : jugadores) {
    		try {
    			s.sendMessage(new TextMessage(message));
    		} catch (Exception e) {
    			log.warn("No se pudo enviar el mensaje a " 
    					+ s.getPrincipal().getName() + ":", e);
    		}
    	}
    }
	
	protected void mensajePrivado(String mensaje,WebSocketSession jugador) {
		
		log.info("server " + mensaje);

		try {
			jugador.sendMessage(new TextMessage(mensaje));
		} catch (Exception e) {
			log.warn("No se pudo enviar el mensaje a " 
					+ jugador.getPrincipal().getName() + ":", e);
		}
    	
    }
	
	protected User encuentraUsuario(WebSocketSession session) {
		return (User)entityManager.createNamedQuery("getUsuario")
				   .setParameter("loginParam", session.getPrincipal().getName())
		           .getSingleResult();
	}
	
	protected int buscaPartida(long id) {
		
		int i = 0;
		while(i < partidas.size() && partidas.get(i).juego.getId() != id) {	
			i++;
		}

		if(i !=  partidas.size()) {
			return i;
		}
		
		return -1;
	}
	
	protected void reparteCartas(int i, int numCartas) {
		
		String mensaje = "";
		
		/*
		for(int j = 0; j < numCartas;j++) {
			jugador.getMano().add(p.juego.getBaraja().daUnaCarta());
			jugador.getMano().add(p.juego.getBaraja().daUnaCarta());
	    }
		*/
		
		for(int l = 0; l < partidas.get(i).juego.getJugadores().size();l++) {
			
			for(int m = 0; m < partidas.get(i).juego.getJugadores().get(l).getMano().size();m++) {
				mensaje +="["+ partidas.get(i).juego.getJugadores().get(l).getMano().get(m).getPalo().toString() +"] ";
				mensaje +="["+ partidas.get(i).juego.getJugadores().get(l).getMano().get(m).getValor().toString()+"] ";
			}
			
			mensajePrivado("server mano "+mensaje,partidas.get(i).jugadoresSk.get(l));
		}
	}
}

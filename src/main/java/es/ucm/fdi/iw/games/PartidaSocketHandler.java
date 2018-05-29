package es.ucm.fdi.iw.games;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import es.ucm.fdi.iw.model.User;

public abstract class PartidaSocketHandler extends TextWebSocketHandler {

	@Autowired
	EntityManager entityManager;
	Logger log;	
	ArrayList<Partida> partidas;
	int cartasIniciales;
	
	PartidaSocketHandler(int cartasIniciales){
		this.log = Logger.getLogger(PartidaSocketHandler.class);
		this.partidas  = new ArrayList<Partida>();
		this.cartasIniciales = cartasIniciales;
	}
		
	void broadcast(String message,List<WebSocketSession> jugadores) {
		log.info("broadcasting: " + message);
		String emisor = message.split(" ")[0];
		
		for (WebSocketSession s : jugadores) {
			try {
				//evita mandarse mensaje a si mismo
				if(!emisor.equals(s.getPrincipal().getName()))
					s.sendMessage(new TextMessage(message));
			} catch (Exception e) {
				log.warn("No se pudo enviar el mensaje a " + s.getPrincipal().getName() + ":", e);
			}
		}
	}
	
	void mensajePrivado(String mensaje,WebSocketSession jugador) {
		
		log.info("mensaje privado: " + mensaje);

		try {
			jugador.sendMessage(new TextMessage(mensaje));
		} catch (Exception e) {
			log.warn("No se pudo enviar el mensaje a " 
					+ jugador.getPrincipal().getName() + ":", e);
		}
    	
    }
	
	private User encuentraUsuario(WebSocketSession session) {
		return (User)entityManager.createNamedQuery("getUsuario")
				   .setParameter("loginParam", session.getPrincipal().getName())
		           .getSingleResult();
	}
	
	Object[] buscaIndicesPyJ(WebSocketSession session) {
		
		Object[] dev = new Object[3];
		
		dev[0] = encuentraUsuario(session);
		dev[1] = buscaPartida(((User) dev[0]).getPartida().getNombre());
		if((int)dev[1] != -1) {
			dev[2] = partidas.get((int)dev[1]).juego.getJugador(((User) dev[0]).getLogin());
		}else {
			dev[2] = -1;
		}

		return dev;
	}
	
	int buscaPartida(String nombre) {
		
		int i = 0;
		while(i < partidas.size() && partidas.get(i).getNombrePartida() != nombre) {	
			i++;
		}

		if(i !=  partidas.size()) {
			return i;
		}
		
		return -1;
	}
	
	void avisaTurno(int indicePartida) {
		int indiceJugador = partidas.get(indicePartida).juego.getTurno();	
		mensajePrivado("turno ",partidas.get(indicePartida).jugadoresSk.get(indiceJugador));
	}
	
	void entregaCartasATodos(int indicePartida,int cartasIniciales) {
		
		partidas.get(indicePartida).reparteAtodos(cartasIniciales);
		for(int indiceJugador = 0; indiceJugador < partidas.get(indicePartida).juego.getJugadores().size();indiceJugador++) {
			entregaCartasAUno(indicePartida,indiceJugador);
		}
	}
	
	void entregaCartasAUno(int indicePartida,int indiceJugador) {
		
		String mensaje = "";
		
		for(int m = 0; m < partidas.get(indicePartida).juego.getJugadores().get(indiceJugador).getMano().size();m++) {
			
			mensaje +=partidas.get(indicePartida).juego.getJugadores().get(indiceJugador).getMano().get(m).getPalo().toString()+" ";
			
			mensaje +=partidas.get(indicePartida).juego.getJugadores().get(indiceJugador).getMano().get(m).getValor().toString()+" ";
		}
		
		mensajePrivado("mano "+mensaje,partidas.get(indicePartida).jugadoresSk.get(indiceJugador));
	}
	
	boolean salio(int indicePartida) {
		if(!partidas.get(indicePartida).salio()) {
			avisaTurno(indicePartida);
			return false;
		}
		return true;
	}
	
	boolean paso(int indicePartida, WebSocketSession session) {
		
		ArrayList<String> ganadores = partidas.get(indicePartida).paso();
		
		if(ganadores == null) {		
			avisaTurno(indicePartida);
	    	return true;
		}else {
			
			String mensaje = ganadores.toString().substring(1, ganadores.toString().length()-1);
			broadcast("acabo " + mensaje.replace(",", ""),partidas.get(indicePartida).jugadoresSk);

			try {
				TimeUnit.SECONDS.sleep(10);
				partidas.get(indicePartida).reiniciaPartida();
				broadcast("empezo ",partidas.get(indicePartida).jugadoresSk);
				entregaCartasATodos(indicePartida, cartasIniciales);
				avisaTurno(indicePartida);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return false;
		}
	}
}

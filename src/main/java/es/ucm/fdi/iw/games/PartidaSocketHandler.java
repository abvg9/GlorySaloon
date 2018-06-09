package es.ucm.fdi.iw.games;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public abstract class PartidaSocketHandler extends TextWebSocketHandler {

	Logger log;	
	ArrayList<Partida> partidas;
	int cartasIniciales;
	boolean hayJugadores;
	private int tiempoDeEspera;
	
	PartidaSocketHandler(int cartasIniciales){
		this.log = Logger.getLogger(PartidaSocketHandler.class);
		this.partidas  = new ArrayList<Partida>();
		this.cartasIniciales = cartasIniciales;
		this.hayJugadores = true;
		this.tiempoDeEspera = 10;
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
		
	int[] buscaIndicesPyJ(String nombre) {
		
		if(partidas.size() > 0) {
			int indicePartida = -1;
			int indiceJugador = 0;
			do {
				indicePartida++;		
				indiceJugador = partidas.get(indicePartida).buscaJugador(nombre);
							
			}while(indicePartida < partidas.size() && indiceJugador == -1);
			
			if(indiceJugador != -1) {
				return new int[]{indicePartida, indiceJugador};
			}
		}
		
		return new int[]{-1, -1};
		
	}
	
	int buscaPartida(String nombre) {
		int i = 0;	
		while(i < partidas.size() && !partidas.get(i).getNombre().equals(nombre)) {
			i++;
		}
		
		if(i!=partidas.size())
			return i;
		
		return -1;
	}
		
	void avisaTurno(int indicePartida) {
		int indiceJugador = partidas.get(indicePartida).getJuego().getTurno();	
		mensajePrivado("turno ",partidas.get(indicePartida).getJugadoresSk().get(indiceJugador));
	}
	
	void entregaCartasATodos(int indicePartida,int cartasIniciales) {
		
		partidas.get(indicePartida).reparteAtodos(cartasIniciales);
		for(int indiceJugador = 0; indiceJugador < partidas.get(indicePartida).getJuego().getJugadores().size();indiceJugador++) {
			entregaCartasAUno(indicePartida,indiceJugador);
		}
	}
	
	void entregaCartasAUno(int indicePartida,int indiceJugador) {
		
		String mensaje = "";
		
		for(int m = 0; m < partidas.get(indicePartida).getJuego().getJugadores().get(indiceJugador).getMano().size();m++) {
			
			mensaje +=partidas.get(indicePartida).getJuego().getJugadores().get(indiceJugador).getMano().get(m).getPalo().toString()+" ";
			
			mensaje +=partidas.get(indicePartida).getJuego().getJugadores().get(indiceJugador).getMano().get(m).getValor().toString()+" ";
		}
		
		mensajePrivado("mano "+mensaje,partidas.get(indicePartida).getJugadoresSk().get(indiceJugador));
	}
	
	boolean salioYtermino(int indicePartida, int indiceJugador) {
		
		if(!partidas.get(indicePartida).salioYtermino(indiceJugador)) {
			if(partidas.get(indicePartida).getJuego().getTurno() == indiceJugador) {
				partidas.get(indicePartida).getJuego().avanzaTurno();
				avisaTurno(indicePartida);
			}
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
			
			String mensaje = ganadores.toString().substring(1, ganadores.toString().length()-1).replace(",", "");
			broadcast("acabo " + mensaje,partidas.get(indicePartida).getJugadoresSk());

			try {
				TimeUnit.SECONDS.sleep(tiempoDeEspera);
				if(hayJugadores) {
					partidas.get(indicePartida).reiniciaPartida();
					broadcast("empezo ",partidas.get(indicePartida).getJugadoresSk());
					entregaCartasATodos(indicePartida, cartasIniciales);
					avisaTurno(indicePartida);
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return false;
		}
	}
}

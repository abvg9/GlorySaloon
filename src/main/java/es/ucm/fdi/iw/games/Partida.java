package es.ucm.fdi.iw.games;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.web.socket.WebSocketSession;

import es.ucm.fdi.iw.common.enums.Respuesta;

public class Partida {
	
	public Juego juego;
	public List<WebSocketSession> jugadoresSk;
	private String nombrePartida;
	
	public Partida(Juego juego, WebSocketSession session, String nombrePartida){
		this.nombrePartida = nombrePartida;
		this.juego = juego;
		this.jugadoresSk = new CopyOnWriteArrayList<>();
		this.jugadoresSk.add(session);
	}
	
	public String getNombrePartida() {
		return nombrePartida;
	}

	public void setNombrePartida(String nombrePartida) {
		this.nombrePartida = nombrePartida;
	}
	
	void añadeJugador(Jugador jugador, WebSocketSession session) {
		juego.getJugadores().add(jugador);
		jugadoresSk.add(session);
	}
		
	Respuesta reparteAuno(int numCartas, int indiceJugador) {		
		return juego.reparteCartasAUno(numCartas, indiceJugador);
	}
	
	Respuesta reparteAtodos(int numCartas) {	
		return juego.reparteCartasATodos(numCartas);
	}
	
	void reiniciaPartida() {
		juego.reiniciaJuego();
	}

	void aposto(Double apostado,int indiceJugador) {
		juego.getJugadores().get(indiceJugador).setApostado(apostado);
		juego.getJugadores().get(indiceJugador).setDinero(juego.getJugadores().get(indiceJugador).getDinero()-apostado);
		juego.setTotalApostado(juego.getTotalApostado() + apostado);
		
	}
	
	boolean salio() {
		if(!juego.partidaAcabada()) {
			juego.avanzaTurno();
			return false;
		}else {
			return true;
		}
	}
			
	ArrayList<String> paso() {

		ArrayList<String> ganadores = new ArrayList<String>();
		if(!juego.partidaAcabada()) {
		   juego.avanzaTurno();
		   return null;
	    }else {
	    	
		   ganadores = juego.getReglas().mejorJugada(juego.getJugadores());
		   juego.repartirBotin(ganadores);
		   juego.setTotalApostado(0.0);
		   return ganadores;
	    }
	}
}

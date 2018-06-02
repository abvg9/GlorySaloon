package es.ucm.fdi.iw.games;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.web.socket.WebSocketSession;

import es.ucm.fdi.iw.common.enums.Respuesta;

public class Partida {
	
	private Juego juego;
	private List<WebSocketSession> jugadoresSk;
	private String nombrePartida;
	
	public Partida(Juego juego, WebSocketSession session, String nombrePartida){
		this.nombrePartida = nombrePartida;
		this.juego = juego;
		this.jugadoresSk = new CopyOnWriteArrayList<>();
		this.jugadoresSk.add(session);
	}
	
	public String getNombre() {
		return nombrePartida;
	}

	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public List<WebSocketSession> getJugadoresSk() {
		return jugadoresSk;
	}

	public void setJugadoresSk(List<WebSocketSession> jugadoresSk) {
		this.jugadoresSk = jugadoresSk;
	}

	public void setNombrePartida(String nombrePartida) {
		this.nombrePartida = nombrePartida;
	}
	
	void anadeJugador(Jugador jugador, WebSocketSession session) {
		juego.getJugadores().add(jugador);
		jugadoresSk.add(session);
	}
		
	Respuesta reparteAuno(int numCartas, int indiceJugador) {		
		return juego.reparteCartasAUno(numCartas, indiceJugador);
	}
	
	Respuesta reparteAtodos(int numCartas) {	
		return juego.reparteCartasATodos(numCartas);
	}
	
	int buscaJugador(String nombre) {
		return juego.getJugador(nombre);
	}
	
	void reiniciaPartida() {
		juego.reiniciaJuego();
	}

	void aposto(int apostado,int indiceJugador) {
		juego.getJugadores().get(indiceJugador).setApostado(apostado);
		juego.getJugadores().get(indiceJugador).setDinero(juego.getJugadores().get(indiceJugador).getDinero()-apostado);
		juego.setTotalApostado(juego.getTotalApostado() + apostado);
		
	}
	
	void donaAjugador(int indiceJugador,int cantidad) {
		juego.donaAjugador(indiceJugador, cantidad);
	}
	
	boolean salioYtermino(int indiceJugador) {
		jugadoresSk.remove(indiceJugador);
		if(!juego.salioYtermino(indiceJugador)) {
			return false;
		}else {
			return true;
		}
	}
			
	ArrayList<String> paso() {

		ArrayList<String> ganadores = new ArrayList<String>();
		if(!juego.rondaAcabada()) {
		   juego.avanzaTurno();
		   return null;
	    }else {
	    	
		   ganadores = juego.getReglas().mejorJugada(juego.getJugadores());
		   juego.repartirBotin(ganadores);
		   juego.setTotalApostado(0);
		   return ganadores;
	    }
	}
}

package es.ucm.fdi.iw.games.movimientos;

import java.io.IOException;

import org.springframework.web.socket.WebSocketSession;

import es.ucm.fdi.iw.games.barajas.Baraja;
import es.ucm.fdi.iw.games.logica.Jugador;

public abstract class Movimiento {
	
	protected Jugador jugador;
	protected Baraja baraja;

	public Movimiento(Jugador jugador,Baraja baraja) {
		this.jugador = jugador;
		this.baraja = baraja;
	}
	
	public Jugador getJugador() {
		return this.jugador;
	}
	
	public Baraja getBaraja() {
		return this.baraja;
	}
	
	public abstract Movimiento ejecutar(WebSocketSession session) throws IOException;

}

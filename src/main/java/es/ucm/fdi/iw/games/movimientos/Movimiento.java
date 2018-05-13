package es.ucm.fdi.iw.games.movimientos;

import javax.servlet.http.HttpSession;

import es.ucm.fdi.iw.games.logica.Jugador;

public abstract class Movimiento {
	
	protected HttpSession session;
	protected Jugador jugador;

	public Movimiento(HttpSession session,Jugador jugador) {
		this.session = session;
		this.jugador = jugador;
	}
	
	
	public abstract Jugador ejecutar();

}

package es.ucm.fdi.iw.games.movimientos;

import org.springframework.web.socket.WebSocketSession;
import es.ucm.fdi.iw.games.barajas.Baraja;
import es.ucm.fdi.iw.games.logica.Jugador;

public class Apostar extends Movimiento {

	private double cantidad;

	public Apostar(Jugador jugador,double cantidad,Baraja baraja) {
		super(jugador, baraja);
		this.cantidad = cantidad;
	}

	@Override
	public Movimiento ejecutar(WebSocketSession session) {
		
		jugador.setApostado(cantidad);
		jugador.setDinero(jugador.getDinero()-cantidad);
		return this;
	}

}

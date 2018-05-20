package es.ucm.fdi.iw.games.movimientos;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import es.ucm.fdi.iw.games.barajas.Baraja;
import es.ucm.fdi.iw.games.barajas.Carta;
import es.ucm.fdi.iw.games.logica.Jugador;

public class PedirCarta extends Movimiento {


	public PedirCarta(Jugador jugador,Baraja baraja) {
		super(jugador, baraja);
	}

	@Override
	public Movimiento ejecutar(WebSocketSession session) throws IOException {
		
		Carta c = baraja.daUnaCarta();
		
		jugador.getMano().add(c);
		
		String mensaje = "server carta ["+c.getPalo().toString()+"]["+c.getValor().toString()+"]";
		session.sendMessage(new TextMessage(mensaje));
		
		return this;
	}



}

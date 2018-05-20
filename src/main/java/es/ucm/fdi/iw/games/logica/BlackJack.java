package es.ucm.fdi.iw.games.logica;

import java.util.ArrayList;
import es.ucm.fdi.iw.common.enums.Juegos;
import es.ucm.fdi.iw.games.barajas.Baraja;
import es.ucm.fdi.iw.games.reglas.Reglas;

public class BlackJack extends Juego {

	public BlackJack(Juegos juego, ArrayList<Jugador> jugadores, Baraja baraja, Reglas reglas,long id) {
		super(juego, jugadores, baraja, reglas,id);
	}

}

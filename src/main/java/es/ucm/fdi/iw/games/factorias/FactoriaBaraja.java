package es.ucm.fdi.iw.games.factorias;

import es.ucm.fdi.iw.common.enums.Juegos;
import es.ucm.fdi.iw.games.barajas.Baraja;
import es.ucm.fdi.iw.games.barajas.Española;
import es.ucm.fdi.iw.games.barajas.Francesa;

public class FactoriaBaraja {

	@SuppressWarnings("incomplete-switch")
	public static Baraja baraja(Juegos juego){
		
		switch(juego){
			case BlackJack: return new Española();
			case Pocker: return new Francesa();
		}
		
		return null;
	}
}

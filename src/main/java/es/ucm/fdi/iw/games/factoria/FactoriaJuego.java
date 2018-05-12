package es.ucm.fdi.iw.games.factoria;

import java.util.ArrayList;

import es.ucm.fdi.iw.common.enums.Juegos;
import es.ucm.fdi.iw.games.logica.BlackJack;
import es.ucm.fdi.iw.games.logica.Juego;
import es.ucm.fdi.iw.games.logica.Jugador;
import es.ucm.fdi.iw.games.logica.Pocker;


public final class FactoriaJuego {
	
	@SuppressWarnings("incomplete-switch")
	public static Juego juego(Juegos juego, ArrayList<Jugador> jugadores){
		
		switch(juego){
			case BlackJack: return new BlackJack(jugadores); 
			case Pocker: return new Pocker(jugadores);
		}
		
		return null;
	}
}

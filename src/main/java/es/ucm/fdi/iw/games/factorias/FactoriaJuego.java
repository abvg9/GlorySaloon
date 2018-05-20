package es.ucm.fdi.iw.games.factorias;

import java.util.ArrayList;
import es.ucm.fdi.iw.common.enums.Juegos;
import es.ucm.fdi.iw.games.logica.BlackJack;
import es.ucm.fdi.iw.games.logica.Juego;
import es.ucm.fdi.iw.games.logica.Jugador;


public final class FactoriaJuego {
	
	public static Juego juego(Juegos juego, ArrayList<Jugador> jugadores,long id){
		
		switch(juego){
			case BlackJack: return new BlackJack(juego,jugadores, FactoriaBaraja.baraja(juego), FactoriaRegla.reglas(juego),id);
			default: return null;
		}
	}
}

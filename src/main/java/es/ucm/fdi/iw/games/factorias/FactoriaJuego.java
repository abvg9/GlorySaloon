package es.ucm.fdi.iw.games.factorias;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import es.ucm.fdi.iw.common.enums.Juegos;
import es.ucm.fdi.iw.games.logica.BlackJack;
import es.ucm.fdi.iw.games.logica.Juego;
import es.ucm.fdi.iw.games.logica.Jugador;
import es.ucm.fdi.iw.games.logica.Pocker;


public final class FactoriaJuego {
	
	public static Juego juego(Juegos juego, ArrayList<Jugador> jugadores,HttpSession session){
		
		switch(juego){
			case BlackJack: return new BlackJack(juego,jugadores, FactoriaBaraja.baraja(juego), FactoriaRegla.reglas(juego),session);
			case Pocker: return new Pocker(juego,jugadores, FactoriaBaraja.baraja(juego), FactoriaRegla.reglas(juego),session);
			case Amigos: return null;
		}
		return null;
	}
}

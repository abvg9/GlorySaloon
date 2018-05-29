package es.ucm.fdi.iw.games.factorias;

import es.ucm.fdi.iw.common.enums.Juegos;
import es.ucm.fdi.iw.games.Reglas;
import es.ucm.fdi.iw.games.ReglasBlackJack;

public class FactoriaRegla {

	public static Reglas reglas(Juegos juego){
		
		switch(juego){
			case BlackJack: return new ReglasBlackJack();
			default : return null;
		}
	}
}

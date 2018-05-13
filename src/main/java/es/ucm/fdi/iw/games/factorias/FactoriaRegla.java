package es.ucm.fdi.iw.games.factorias;

import es.ucm.fdi.iw.common.enums.Juegos;
import es.ucm.fdi.iw.games.reglas.Reglas;
import es.ucm.fdi.iw.games.reglas.ReglasBlackJack;
import es.ucm.fdi.iw.games.reglas.ReglasPocker;

public class FactoriaRegla {

	@SuppressWarnings("incomplete-switch")
	public static Reglas reglas(Juegos juego){
		
		switch(juego){
			case BlackJack: return new ReglasBlackJack();
			case Pocker: return new ReglasPocker();
		}
		
		return null;
	}
}

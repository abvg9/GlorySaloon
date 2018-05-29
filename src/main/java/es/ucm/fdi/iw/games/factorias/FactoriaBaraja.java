package es.ucm.fdi.iw.games.factorias;

import es.ucm.fdi.iw.common.enums.Barajas;
import es.ucm.fdi.iw.games.Baraja;
import es.ucm.fdi.iw.games.Española;
import es.ucm.fdi.iw.games.Francesa;

public class FactoriaBaraja {

	public static Baraja baraja(Barajas baraja){
		
		switch(baraja){
			case Francesa: 
				return new Francesa();
			case Española: 
				return new Española();
			default : return null;
		}
	}
}

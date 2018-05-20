package es.ucm.fdi.iw.games.barajas;

import java.util.ArrayList;

public class Francesa extends Baraja{

	public enum Palos{picas, diamantes, corazones, trevoles}
	
	public enum Valores{_2, _3, _4, _5, _6, _7, _8, _9, _10, J, Q, K, A}
	
	public Francesa() {
		totalCartas = 52;
		cartasDadas = 0;
		cartas = new ArrayList<Carta>(totalCartas);
		for(int i = 0; i < 13;i++){		
			for(int j = 0; j < 4;j++){
				cartas.set(i,new CartaGenerica());
				cartas.get(i).palo = Palos.values()[j];
				cartas.get(i).valor = Valores.values()[j];
			}
		}
	}

}

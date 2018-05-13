package es.ucm.fdi.iw.games.barajas;

import java.util.ArrayList;

public class Francesa extends Baraja{

	public enum Palos{picas, diamantes, corazones, trevoles}
	
	public enum Valores{_2, _3, _4, _5, _6, _7, _8, _9, _10, J, Q, K, A}
	
	public Francesa() {
		totalCartas = 52;
		cartas = new ArrayList<Carta>(totalCartas);
		for(int i = 0; i < 13;i++){		
			for(int j = 0; j < 4;j++){
				cartas.get(i).palo = Palos.values()[j];
				cartas.get(i).valor = Valores.values()[j];
			}
		}
	}
	
	public static int parseIntPalo(Palos palos) {
		 switch(palos){
	        case picas: return 0;
	        case diamantes: return 1;
	        case corazones: return 2;
	        case trevoles: return 3;
	        default : return -1;
	    }
	}

	public static int parseIntNumber(Valores valores) {
		 switch(valores){
	        case _2: return 2;
	        case _3: return 3;
	        case _4: return 4;
	        case _5: return 5;
	        case _6: return 6;
	        case _7: return 7;
	        case _8: return 8;
	        case _9: return 9;
	        case _10: return 10;
	        case J: return 11;
	        case Q: return 12;
	        case K: return 13;
	        case A: return 14;
	        default : return -1;
		 }
	}
}

package es.ucm.fdi.iw.games.barajas;
import java.util.ArrayList;

public class Española extends Baraja {

	public enum Palos{oros, copas, espadas, bastos}
	
	public enum Valores{_1, _2, _3, _4, _5, _6, _7, _8, _9, sota, caballo, rey}
	
	public Española() {
		totalCartas = 48;
		cartas = new ArrayList<Carta>(totalCartas);
		for(int i = 0; i < 12;i++){		
			for(int j = 0; j < 4;j++){
				cartas.get(i).palo = Palos.values()[j];
				cartas.get(i).valor = Valores.values()[j];
			}
		}
	}

}

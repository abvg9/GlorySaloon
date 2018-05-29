package es.ucm.fdi.iw.games;

public class Española extends Baraja {

	private enum Palos{oros, copas, espadas, bastos}
	
	private enum Valores{_1, _2, _3, _4, _5, _6, _7, _8, _9, sota, caballo, rey}
	
	public Española() {
		super(48);
		for(int i = 0; i < 12;i++){		
			for(int j = 0; j < 4;j++){
				cartas.add(new Carta(Palos.values()[j],Valores.values()[i]));
			}
		}
	}

}

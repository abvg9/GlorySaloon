package es.ucm.fdi.iw.games;

public class Francesa extends Baraja{

	private enum Palos{picas, diamantes, corazones, trevoles}
	
	private enum Valores{_2, _3, _4, _5, _6, _7, _8, _9, _10, J, Q, K, A}
		
	public Francesa() {
		super(52);
		for(int i = 0; i < 13;i++){		
			for(int j = 0; j < 4;j++){
				cartas.add(new Carta(Palos.values()[j],Valores.values()[i]));
			}
		}
	}

}

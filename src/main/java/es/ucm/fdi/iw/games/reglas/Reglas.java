package es.ucm.fdi.iw.games.reglas;

import java.util.ArrayList;
import es.ucm.fdi.iw.games.barajas.Carta;
import es.ucm.fdi.iw.games.logica.Jugador;

public abstract class Reglas {
	
	public abstract ArrayList<Jugador> mejorJugada(ArrayList<Jugador> jugadores);
	protected abstract int comparaJugada(ArrayList<Carta> mano1,ArrayList<Carta> mano2);
	
}

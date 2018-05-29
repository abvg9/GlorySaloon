package es.ucm.fdi.iw.games;

import java.util.ArrayList;

import es.ucm.fdi.iw.common.enums.Respuesta;

public abstract class Reglas {
	
	abstract ArrayList<String> mejorJugada(ArrayList<Jugador> jugadores);
	abstract int comparaJugada(ArrayList<Carta> mano1,ArrayList<Carta> mano2);
	abstract Respuesta ccpp(ArrayList<Carta> mano); // cumple condiciones para pedir
	
}

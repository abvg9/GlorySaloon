package es.ucm.fdi.iw.games.reglas;

import java.util.ArrayList;
import es.ucm.fdi.iw.games.barajas.Carta;
import es.ucm.fdi.iw.games.logica.Jugador;

public final class ReglasBlackJack extends Reglas {

	@Override
	public ArrayList<Jugador> mejorJugada(ArrayList<Jugador> jugadores) {

		ArrayList<Integer> puntuaciones = new ArrayList<Integer>(jugadores.size());	
		ArrayList<Jugador> ganadores = new ArrayList<Jugador>(jugadores.size());
		int suma = 0;
		int mejor = 0;
		Jugador jugGanador = null;
		
		for(int i = 0; i < jugadores.size();i++) {
			suma = sumaMano(jugadores.get(i).getMano());
			puntuaciones.add(sumaMano(jugadores.get(i).getMano()));
			if(suma > mejor) {
				mejor = suma;
				jugGanador = jugadores.get(i);
			}
		}
		
		ganadores.add(jugGanador);
		
		for(int i = 0; i < jugadores.size();i++) {
			
			if(comparaJugada(jugGanador.getMano(),jugadores.get(i).getMano()) == 1) {
				ganadores.add(jugadores.get(i));	
			}
			
		}
		
		return ganadores;
	}

	@Override
	protected int comparaJugada(ArrayList<Carta> mano1, ArrayList<Carta> mano2) {
		return (sumaMano(mano1) == sumaMano(mano2)) ? 1 : 0;
	}

	private int sumaMano(ArrayList<Carta> mano) {
		
		int valorTotal = 0;
		int valor = 0;
		for(int i = 0; i < mano.size();i++) {
			valor = mano.get(0).getValor().ordinal()+2;
			if(valor >= 10) {
				valorTotal += 10;
			}
			valorTotal += valor;
		}
		
		return valorTotal;
	}
}

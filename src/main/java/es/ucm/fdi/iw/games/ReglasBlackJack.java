package es.ucm.fdi.iw.games;

import java.util.ArrayList;

import es.ucm.fdi.iw.common.enums.Respuesta;

public final class ReglasBlackJack extends Reglas {
	
	private final int maximo = 21;

	@Override
	ArrayList<String> mejorJugada(ArrayList<Jugador> jugadores) {

		ArrayList<String> ganadores = new ArrayList<String>(jugadores.size());
		int suma = 0;
		int mejor = -2;
		Jugador jugGanador = null;
		String mano;
		
		for(int i = 0; i < jugadores.size();i++) {
			suma = sumaMano(jugadores.get(i).getMano());
			if(suma > mejor) {
				mejor = suma;
				jugGanador = jugadores.get(i);
			}
		}
		
		suma = 0;
		for(int i = 0; i < jugadores.size();i++) {
			
			if(comparaJugada(jugGanador.getMano(),jugadores.get(i).getMano()) == 1) {
				mano = "";
				for(int j = 0; j < jugadores.get(i).getMano().size();j++) {
					mano += 
					"{"+jugadores.get(i).getMano().get(j).palo.toString() +"/"+jugadores.get(i).getMano().get(j).valor.toString() +"}";
				}		
				ganadores.add(jugadores.get(i).getNombre());
				ganadores.add(mano);
				suma++;
			}
			
		}
		ganadores.add(String.valueOf(suma));
		
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
			valor = mano.get(i).getValor().ordinal()+2;
			if(valor > 10) {
				valorTotal += 10;
			}else {
				valorTotal += valor;
			}
		}
		
		if(valorTotal > maximo) {
			return -1;
		}else {
			return valorTotal;
		}
	}

	@Override
	Respuesta ccpp(ArrayList<Carta> mano) {
		if(sumaMano(mano) == -1) {
			return Respuesta.TePasaste;
		}
		return Respuesta.TodoCorrecto;
	}
}

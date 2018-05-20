package es.ucm.fdi.iw.games.factorias;

import es.ucm.fdi.iw.common.enums.Movimientos;
import es.ucm.fdi.iw.games.barajas.Baraja;
import es.ucm.fdi.iw.games.logica.Jugador;
import es.ucm.fdi.iw.games.movimientos.Apostar;
import es.ucm.fdi.iw.games.movimientos.Movimiento;
import es.ucm.fdi.iw.games.movimientos.PedirCarta;

public class FactoriaMovimiento {

	public static Movimiento movimiento(Movimientos movimiento,Double cantidad,Jugador jugador,Baraja baraja){
		
		switch(movimiento){
			case apostar: return new Apostar(jugador, cantidad,baraja);
			case pedirCartas: return new PedirCarta(jugador,baraja);
		}
		return null;
	}
}

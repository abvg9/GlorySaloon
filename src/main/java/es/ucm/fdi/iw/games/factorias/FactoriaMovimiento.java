package es.ucm.fdi.iw.games.factorias;

import javax.servlet.http.HttpSession;

import es.ucm.fdi.iw.common.enums.Movimientos;
import es.ucm.fdi.iw.games.logica.Jugador;
import es.ucm.fdi.iw.games.movimientos.Apostar;
import es.ucm.fdi.iw.games.movimientos.Movimiento;
import es.ucm.fdi.iw.games.movimientos.PedirCarta;
import es.ucm.fdi.iw.games.movimientos.Plantarse;

public class FactoriaMovimiento {

	public static Movimiento movimiento(Movimientos movimiento,HttpSession session,Double cantidad,Jugador jugador){
		
		switch(movimiento){
			case apostar: return new Apostar(session, jugador, cantidad);
			case pedirCartas: return new PedirCarta(session, jugador);
			case plantarse: return new Plantarse(session, jugador);
		}
		return null;
	}
}

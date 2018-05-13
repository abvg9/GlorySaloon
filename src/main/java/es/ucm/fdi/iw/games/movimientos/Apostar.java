package es.ucm.fdi.iw.games.movimientos;

import javax.servlet.http.HttpSession;

import es.ucm.fdi.iw.common.utils.CargaAtributos;
import es.ucm.fdi.iw.games.logica.Jugador;

public class Apostar extends Movimiento {

	private double cantidad;

	public Apostar(HttpSession session, Jugador jugador,double cantidad) {
		super(session, jugador);
		this.cantidad = cantidad;
	}

	@Override
	public Jugador ejecutar() {
		if(cantidad <= jugador.getDinero()) {
			jugador.setApostado(cantidad);
			jugador.setDinero(jugador.getDinero()-cantidad);
			session.setAttribute(CargaAtributos.cantidadApostada, cantidad);
			session.setAttribute(CargaAtributos.totalApostado, (double)session.getAttribute(CargaAtributos.totalApostado)+cantidad);		
		}else {
			session.setAttribute(CargaAtributos.mensaje, "No tienes "+ cantidad);
		}
		return jugador;
	}

}

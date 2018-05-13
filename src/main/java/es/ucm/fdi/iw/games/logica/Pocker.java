package es.ucm.fdi.iw.games.logica;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import es.ucm.fdi.iw.common.enums.Juegos;
import es.ucm.fdi.iw.games.barajas.Baraja;
import es.ucm.fdi.iw.games.reglas.Reglas;


public class Pocker extends Juego {
	
	public Pocker(Juegos juego, ArrayList<Jugador> jugadores,Baraja baraja,Reglas reglas,HttpSession session) {
		super(juego, jugadores, baraja, reglas,session);
	}

	private double ciega;

	public double getCiega() {
		return ciega;
	}

	public void setCiega(double ciega) {
		this.ciega = ciega;
	}

	@Override
	public void run() {
		
		
		
	}


}

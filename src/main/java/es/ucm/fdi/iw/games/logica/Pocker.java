package es.ucm.fdi.iw.games.logica;

import java.util.ArrayList;

import es.ucm.fdi.iw.games.barajas.Francesa;
import es.ucm.fdi.iw.games.reglas.ReglasPocker;


public class Pocker extends Juego {
	
	private double ciega;

	public Pocker(ArrayList<Jugador> jugadores){
		maxJugadores = 7;
		baraja = new Francesa();
		this.jugadores = jugadores;
		totalApostado = 0;
		turnoActual = 0;
		setCiega(0);
		reglas = new ReglasPocker();
	}
	
	@Override
	protected int siguienteTurno(Jugador jugador) {
		return 0;
	}

	@Override
	protected void apuesta(Jugador j) {
		// TODO Auto-generated method stub
		
	}

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

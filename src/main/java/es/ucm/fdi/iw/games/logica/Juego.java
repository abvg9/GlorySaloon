package es.ucm.fdi.iw.games.logica;

import java.util.ArrayList;
import es.ucm.fdi.iw.games.barajas.Baraja;
import es.ucm.fdi.iw.games.reglas.Reglas;


public abstract class Juego {
	
	protected Baraja baraja;
	protected int maxJugadores;
	protected Reglas reglas;
	protected ArrayList<Jugador> jugadores;
	protected double totalApostado;
	protected int turnoActual;
		
	public Baraja getMazo(){return this.baraja;}
	public void setMazo(Baraja baraja){this.baraja = baraja;}
	
	public int getMaxJugadores(){return this.maxJugadores;}
	public void setMaxJugadores(int maxJugadores){this.maxJugadores = maxJugadores;}
	
	public Reglas getReglas(){return this.reglas;}
	public void setReglas(Reglas reglas){this.reglas = reglas;}
	
	public int getTurnoActual(){return this.turnoActual;}
	public void setTurnoActual(int turnoActual){this.turnoActual = turnoActual;}
	
	protected abstract int siguienteTurno(Jugador jugador);
	
	protected abstract void apuesta(Jugador j);
	
	public abstract void run();
		
}

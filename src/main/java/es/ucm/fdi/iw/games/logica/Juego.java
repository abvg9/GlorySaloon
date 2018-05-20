package es.ucm.fdi.iw.games.logica;

import java.util.ArrayList;
import es.ucm.fdi.iw.common.enums.Juegos;
import es.ucm.fdi.iw.games.barajas.Baraja;
import es.ucm.fdi.iw.games.barajas.Carta;
import es.ucm.fdi.iw.games.barajas.Francesa;
import es.ucm.fdi.iw.games.factorias.FactoriaRegla;
import es.ucm.fdi.iw.games.reglas.Reglas;


public abstract class Juego{
	
	protected Baraja baraja;
	protected Reglas reglas;
	protected ArrayList<Jugador> jugadores;
	protected Juegos juego;
	protected long id;
	protected double totalApostado;

	Juego(Juegos juego, ArrayList<Jugador> jugadores,Baraja baraja,Reglas reglas,long id) {
		
		this.baraja = new Francesa();
		this.jugadores = jugadores;
		this.reglas = FactoriaRegla.reglas(juego);
		this.juego = juego;
		this.id = id;
		this.totalApostado = 0;
	}
	
	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}
		
	public int getJugador(String nombre) {
		
		int i = 0;	
		while(i < jugadores.size() && jugadores.get(i).nombre != nombre) {
			i++;
		}
		
		if(i!=jugadores.size())
			return i;
		
		return -1;
	}
	
	public long getId() {
		return this.id;
	}
	
	public Baraja getBaraja() {
		return this.baraja;
	}

	public void setBaraja(Baraja baraja) {
		this.baraja = baraja;
	}
	
	public Reglas getReglas() {
		return this.reglas;
	}
	
	public double getTotalApostado() {
		return this.totalApostado;
	}
	
	public void setTotalApostado(double totalApostado) {
		this.totalApostado = totalApostado;
	}
	
	public void setJugadores(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}
	
	public void repartirBotin(ArrayList<Jugador> ganadores){
			
		double porcentaje;
		double ganado;
		
		for(int i = 0; i < jugadores.size();i++) {
			
			if(jugadores.get(i).nombre == ganadores.get(i).nombre) {
				
				porcentaje = (jugadores.get(i).getApostado()*100)/totalApostado;
				ganado = (totalApostado*porcentaje)/100;
				
				jugadores.get(i).setDinero(jugadores.get(i).getDinero() + ganado);	
			}
			jugadores.get(i).setApostado(0.0);
			jugadores.get(i).setMano(new ArrayList<Carta>());
		}
	}
	
	public void reparteCartas(int numDeCartasARepartir,int numJugadores) {
		
	
		for(int i = 0; i < jugadores.size();i++) {
			for(int j = 0; j < numDeCartasARepartir;j++) {
				jugadores.get(i).getMano().add(baraja.daUnaCarta());
			}
		}
	}
	
	
}

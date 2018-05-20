package es.ucm.fdi.iw.games.logica;

import java.util.ArrayList;
import es.ucm.fdi.iw.games.barajas.Carta;


public abstract class Jugador {

	protected ArrayList<Carta> mano;
	protected double dinero;
	protected String nombre;
	protected double apostado;
	protected int turno;
	
	public ArrayList<Carta> getMano(){return this.mano;}
	public void setMano(ArrayList<Carta> mano){this.mano = mano;}
	
	public double getDinero(){return dinero;}
	public void setDinero(double dinero){this.dinero = dinero;}
	
	public String getNombre() {return nombre;}
	public void setNombre(String nombre){this.nombre = nombre;}
	
	public double getApostado(){return this.apostado;}
	public void setApostado(Double apostado){this.apostado = apostado;}

	public int getTurno(){return this.turno;}
	public void setTurno(int turno){this.turno = turno;}
		
}

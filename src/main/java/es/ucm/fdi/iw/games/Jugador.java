package es.ucm.fdi.iw.games;

import java.util.ArrayList;


public class Jugador {

	private ArrayList<Carta> mano;
	private double dinero;
	private String nombre;
	private double apostado;
	
	public Jugador(ArrayList<Carta> mano, double dinero, String nombre, double apostado) {
		this.mano = mano;
		this.dinero = dinero;
		this.nombre = nombre;
		this.apostado = apostado;
	}
	
	public ArrayList<Carta> getMano(){
		return this.mano;
	}
	
	public void setMano(ArrayList<Carta> mano){
		this.mano = mano;
	}
	
	public double getDinero(){
		return dinero;
	}
	
	public void setDinero(double dinero){
		this.dinero = dinero;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public double getApostado(){
		return this.apostado;
	}
	
	public void setApostado(Double apostado){
		this.apostado = apostado;
	}		
}

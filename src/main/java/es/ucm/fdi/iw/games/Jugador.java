package es.ucm.fdi.iw.games;

import java.util.ArrayList;


public class Jugador {

	private ArrayList<Carta> mano;
	private int dinero;
	private String nombre;
	private int apostado;
	
	public Jugador(ArrayList<Carta> mano, int dinero, String nombre, int apostado) {
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
	
	public int getDinero(){
		return dinero;
	}
	
	public void setDinero(int dinero){
		this.dinero = dinero;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public int getApostado(){
		return this.apostado;
	}
	
	public void setApostado(int apostado){
		this.apostado = apostado;
	}		
}

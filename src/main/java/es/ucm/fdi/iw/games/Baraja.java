package es.ucm.fdi.iw.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Baraja {
	
	int totalCartas;
	protected List<Carta> cartas;
	int cartasDadas;
	
	public Baraja(int totalCartas){
		this.totalCartas = totalCartas;
		cartasDadas = 0;
		cartas = new ArrayList<Carta>(totalCartas);
	}
			
	public int getCatasDadas() {
		return this.cartasDadas;
	}
	
	public void setCatasDadas(int cartasDadas){
		this.cartasDadas = cartasDadas;
	}
		
	public int getTotalCartas() {
		return this.totalCartas;
	}
	
	public void setTotalCartas(int totalCartas){
		this.totalCartas = totalCartas;
	} 
	
	public List<Carta> getCartas(){
		return this.cartas;
	}
	
	public void setCartas(List<Carta> cartas){
		this.cartas=cartas;
	}
		
	Carta daUnaCarta() {
			
		if(totalCartas > cartasDadas) {
			int aux = cartasDadas;
			cartasDadas++;	
			return cartas.get(aux);		
		}

		return null;
		
	}
	
	private void barajaCartas(){
		 Collections.shuffle(cartas);
	}
	
	void reiniciaBaraja() {
		cartasDadas = 0;
		barajaCartas();
	}

}

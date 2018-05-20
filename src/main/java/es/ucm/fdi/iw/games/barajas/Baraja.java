package es.ucm.fdi.iw.games.barajas;
import java.util.Collections;
import java.util.List;

public abstract class Baraja {
	
	protected int totalCartas;
	protected List<Carta> cartas;
	protected int cartasDadas;
	protected Enum<?> palo;
	protected Enum<?> valor;
	
	public int getCatasDadas() {return this.cartasDadas;}
	public void setCatasDadas(int cartasDadas) {this.cartasDadas = cartasDadas;}
		
	public int getTotalCartas() {return this.totalCartas;}
	public void setTotalCartas(int totalCartas) {this.totalCartas = totalCartas;} 
	
	public List<Carta> getCartas() {return this.cartas;}
	public void setCartas(List<Carta> cartas) {this.cartas=cartas;}
		
	public Carta daUnaCarta() {
			
		if(totalCartas > cartasDadas) {
			int aux = cartasDadas;
			cartasDadas++;	
			return cartas.get(aux);		
		}

		return null;
		
	}
	
	public void barajaCartas(){
		 Collections.shuffle(cartas);
	}
	
}

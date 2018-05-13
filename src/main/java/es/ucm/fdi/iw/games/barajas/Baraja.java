package es.ucm.fdi.iw.games.barajas;
import java.util.List;

public abstract class Baraja {
	
	protected int totalCartas;
	protected List<Carta> cartas;
	protected int cartasDadas;
	
	public int getCatasDadas() {return this.cartasDadas;}
	public void setCatasDadas(int cartasDadas) {this.cartasDadas = cartasDadas;}
	public void seDioUna() {cartasDadas++;}
	
	public int getTotalCartas() {return this.totalCartas;}
	public void setTotalCartas(int totalCartas) {this.totalCartas = totalCartas;} 
	
	public List<Carta> getCartas() {return this.cartas;}
	public void setCartas(List<Carta> cartas) {this.cartas=cartas;}
}

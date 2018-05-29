package es.ucm.fdi.iw.games;

public class Carta {
	
	protected Enum<?> palo;
	protected Enum<?> valor;
	
	public Carta(Enum<?> palo, Enum<?> valor) {
		this.palo = palo;
		this.valor = valor;
	}

	public Enum<?> getPalo(){return this.palo;}
	public void setPalo(Enum<?> palo){this.palo = palo;}
	
	public Enum<?> getValor(){return this.valor;}
	public void setValor(Enum<?> valor){this.valor = valor;}
		
}

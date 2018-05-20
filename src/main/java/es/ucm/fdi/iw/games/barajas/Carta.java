package es.ucm.fdi.iw.games.barajas;

public abstract class Carta {
	
	protected Enum<?> palo;
	protected Enum<?> valor;
	
	public Enum<?> getPalo(){return this.palo;}
	public void setPalo(Enum<?> palo){this.palo = palo;}
	
	public Enum<?> getValor(){return this.valor;}
	public void setValor(Enum<?> valor){this.valor = valor;}
		
}

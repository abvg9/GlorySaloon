package es.ucm.fdi.iw.games.reglas;

import java.util.ArrayList;
import es.ucm.fdi.iw.games.barajas.Carta;
import es.ucm.fdi.iw.games.barajas.Francesa;
import es.ucm.fdi.iw.games.barajas.Francesa.Palos;
import es.ucm.fdi.iw.games.barajas.Francesa.Valores;
import es.ucm.fdi.iw.games.logica.Jugador;


public final class ReglasPocker extends Reglas {
	
	public enum Jugadas{
		cartaAlta, pareja, doblePareja, trio, escalera, color, full, pocker, escaleraColor, escaleraReal
	}
	
	private class JugadorJugada{
		private Jugador jugador;
		private Jugadas jugada;
		
		JugadorJugada(Jugador jugador,Jugadas jugada){
			this.jugador = jugador;
			this.jugada = jugada;
		}
	}
	
	private Jugadas mejorJugada;
	
	@Override
	public ArrayList<Jugador> mejorJugada(ArrayList<Jugador> jugadores) {
		
		ArrayList<JugadorJugada> juju = new ArrayList<JugadorJugada>();
		mejorJugada = Jugadas.cartaAlta;
		Jugadas jugada;
		
		for(int i = 0; i < jugadores.size();i++){
			
			if(tieneEscaleraReal(jugadores.get(i).getMano())){
				jugada = Jugadas.escaleraReal;
			}else if(tieneEscaleraDeColor(jugadores.get(i).getMano())){
				jugada = Jugadas.escaleraColor;
			}else if(tieneVarias(jugadores.get(i).getMano(),2)!= null && 
					 tieneVarias(jugadores.get(i).getMano(),3)!= null){
				jugada = Jugadas.pocker;
			}else if(tieneVarias(jugadores.get(i).getMano(),4)!= null){
				jugada = Jugadas.pocker;
			}else if(tieneColor(jugadores.get(i).getMano())){
				jugada = Jugadas.pocker;
			}else if(tieneEscalera(jugadores.get(i).getMano())){
				jugada = Jugadas.pocker;
			}else if(tieneVarias(jugadores.get(i).getMano(),3)!= null){
				jugada = Jugadas.pocker;
			}else if(tieneDoblePareja(jugadores.get(i).getMano())){
				jugada = Jugadas.pocker;
			}else if(tieneVarias(jugadores.get(i).getMano(),2)!= null){
				jugada = Jugadas.pocker;
			}else{
				jugada = Jugadas.cartaAlta;
			}

			juju.add(new JugadorJugada(jugadores.get(i),jugada));
			mejorJugada = jugada;
		}

		/*
		/* Vas por aqui
        Collections.sort(juju, new Comparator<Jugadas>() {
            @Override
            public int compare(Jugadas j1, Jugadas j2) {
                return comparaJugada(mano1, mano2);
            }
        });
		
		return ganador;
		*/
		return null;
	}

	@Override
	protected int comparaJugada(ArrayList<Carta> mano1, ArrayList<Carta> mano2) {
		
		if(mejorJugada == Jugadas.cartaAlta || 
		   mejorJugada == Jugadas.escalera ||  
		   mejorJugada == Jugadas.color ||
		   mejorJugada == Jugadas.escaleraReal ){
			
			if(cartaAlta(mano1,null) > cartaAlta(mano2,null)) return 1;
			return 0;
			
		}else{
			if(cartaAlta(mano1,tieneVarias(mano1,2)) > cartaAlta(mano2,tieneVarias(mano2,2))) return 1;
			return 0;
		}
		
	}
	
	private boolean tieneEscaleraReal(ArrayList<Carta> mano){
		
		int j = Francesa.parseIntNumber(Valores._10);
		int i = 0;
		while(i < mano.size() && 
			  mano.get(i).getPalo().equals(Palos.corazones) &&
			  Valores.values()[j+i] == mano.get(i).getValor()){
			i++;
		}
		return i == mano.size();
	}
	
	private boolean tieneEscaleraDeColor(ArrayList<Carta> mano){
		
		Carta c = mano.get(0);

		int i = 1;
		while(i < mano.size() && 
			  mano.get(i).getPalo().equals(c.getPalo()) &&
			  c.getValor().ordinal()+i == mano.get(i).getValor().ordinal()){
			i++;
		}
		return i == mano.size();
	}

	private Carta tieneVarias(ArrayList<Carta> mano,int ap){
		
		int i = 0;
		int j = 0;
		int apariciones = 0;
		Carta c = null;
		while(i < mano.size() && apariciones > ap){
			c = mano.get(i);
			j = i;
			while(j < mano.size() && apariciones > ap){
				if(c.getValor() == mano.get(j).getValor()){
					apariciones++;
				}
				j++;
			}
			i++;
		}
		
		if(apariciones == ap)
			return c;
		
		return null;
	}

	private boolean tieneColor(ArrayList<Carta> mano){
		
		Carta c = mano.get(0);

		int i = 1;
		while(i < mano.size() &&
			  c.getValor().ordinal()+i == mano.get(i).getValor().ordinal()){
			i++;
		}
		return i == mano.size();
	}
	
	private boolean tieneEscalera(ArrayList<Carta> mano){
		
		Carta c = mano.get(0);

		int i = 1;
		while(i < mano.size() && mano.get(i).getPalo().equals(c.getPalo())){
			i++;
		}
		return i == mano.size();
	}

	private boolean tieneDoblePareja(ArrayList<Carta> mano){	
		return tieneVarias(mano,3)==null && tieneVarias(mano,2)!= null && tieneVarias(mano,2)!= null;
	}

	private int cartaAlta(ArrayList<Carta> mano, Carta compara){
		
		Carta c;
		
		if(compara != null)
			 c = mano.get(0);
			 
		c = compara;
		
		for(int i = 1; i < mano.size();i++){
			if(c.getValor().ordinal() < mano.get(i).getValor().ordinal()){
				c = mano.get(i);
			}
		}
		
		return c.getValor().ordinal();
	}

}

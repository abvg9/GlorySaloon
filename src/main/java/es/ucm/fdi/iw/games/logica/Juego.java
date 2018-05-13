package es.ucm.fdi.iw.games.logica;

import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import es.ucm.fdi.iw.common.enums.Juegos;
import es.ucm.fdi.iw.common.utils.CargaAtributos;
import es.ucm.fdi.iw.games.barajas.Baraja;
import es.ucm.fdi.iw.games.barajas.Carta;
import es.ucm.fdi.iw.games.barajas.Francesa;
import es.ucm.fdi.iw.games.factorias.FactoriaRegla;
import es.ucm.fdi.iw.games.reglas.Reglas;


public abstract class Juego extends Thread {
	
	protected Baraja baraja;
	protected Reglas reglas;
	protected ArrayList<Jugador> jugadores;
	protected double totalApostado;
	protected Jugador jugador;
	protected HttpSession session;
	
	Juego(Juegos juego, ArrayList<Jugador> jugadores,Baraja baraja,Reglas reglas,HttpSession session) {
		
		this.baraja = new Francesa();
		this.jugadores = jugadores;
		this.totalApostado = 0;
		this.reglas = FactoriaRegla.reglas(juego);
		this.jugador.apostado = 0;
		this.jugador.dinero = CargaAtributos.u.getDinero();
		this.jugador.turno = jugadores.get(averiguaQuienSoy()).getTurno();
		this.jugador.nombre = CargaAtributos.u.getLogin();
		this.session = session;
	}
	
	protected boolean esHost(){
		return jugador.nombre == jugadores.get(0).nombre;
	}

	protected int siguienteTurno() {
		
		int i = averiguaQuienSoy();
		
		if(i == jugadores.size())
			return 0;
		
		return i+1;
	}
	
	protected void barajaCartas(){
		 Collections.shuffle(baraja.getCartas());
	}
	
	protected int averiguaQuienSoy() {
		
		int i = 0;
		while(jugadores.get(i).nombre != CargaAtributos.u.getLogin()) {
			i++;
		}

		return i;
	}

	protected void daUnaCarta(Carta c, Jugador j) {
		j.mano.add(c);
		baraja.seDioUna();
	}
		
	protected void reparteCartas(int numDeCartasARepartir) {
		
		ArrayList<ArrayList<Carta>> manos = new ArrayList<ArrayList<Carta>>();
		
		for(int i = 0; i < jugadores.size();i++) {
			for(int j = 0; j < numDeCartasARepartir;j++) {
				daUnaCarta(baraja.getCartas().get(baraja.getCatasDadas()), jugadores.get(i));
				baraja.seDioUna();
			}
			manos.add(jugadores.get(i).getMano());
		}
		jugador.mano =  jugadores.get(averiguaQuienSoy()).mano;
		session.setAttribute(CargaAtributos.manos, manos);
	}
	
	public abstract void run();
		
}

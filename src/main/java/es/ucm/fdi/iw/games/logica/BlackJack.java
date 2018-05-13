package es.ucm.fdi.iw.games.logica;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import es.ucm.fdi.iw.common.enums.Juegos;
import es.ucm.fdi.iw.common.enums.Movimientos;
import es.ucm.fdi.iw.common.utils.CargaAtributos;
import es.ucm.fdi.iw.games.barajas.Baraja;
import es.ucm.fdi.iw.games.factorias.FactoriaMovimiento;
import es.ucm.fdi.iw.games.movimientos.Movimiento;
import es.ucm.fdi.iw.games.reglas.Reglas;

public class BlackJack extends Juego {

	private final int cartasEnMano = 2;
	
	public BlackJack(Juegos juego, ArrayList<Jugador> jugadores, Baraja baraja, Reglas reglas,HttpSession session) {
		super(juego, jugadores, baraja, reglas,session);
	}

	@Override
	public void run() {
		
		boolean salir = false;
		
		//Espera a que todos esten listos para jugar.
		
		//mientras que no salga de la partida, haya mas de dos personas jugando
		while(!salir && jugadores.size() > 2) {
			
			// barajamos las cartas(el host/creador de la partida debe hacerlo)
			// si se va el creador, el siguiente que se unio debe ser el que lo haga.
			if(esHost()) {
				barajaCartas();
				baraja.setCatasDadas(0);
			}
			
			//repartimos las cartas(el host/creador de la partida debe hacerlo) y se las damos(en la bd)
			if(jugador.nombre == jugadores.get(0).nombre) {
				reparteCartas(cartasEnMano);
			}
				
			//Esperamos hasta que sea nuestro turno
			while(averiguaQuienSoy() != (Integer)session.getAttribute(CargaAtributos.turno));
			
			//nos quedamos aqui hasta que el usuario realice un movimiento
			while(session.getAttribute(CargaAtributos.movimiento).equals(null));
					
			//ejecutamos el movimiento 	
			Movimiento mov = FactoriaMovimiento.movimiento((Movimientos)session.getAttribute(CargaAtributos.movimiento),
															session, 
															(double)session.getAttribute(CargaAtributos.cantidadApostada), 
															jugador);
			mov.ejecutar();
			
			//pasamos de turno
			session.setAttribute(CargaAtributos.turno, siguienteTurno());
			
			//y por penultimo eliminamos la variable movimiento
			session.setAttribute(CargaAtributos.movimiento, null);
			
			//esperamos a que los demás hayan terminado
			while(averiguaQuienSoy() != (Integer)session.getAttribute(CargaAtributos.turno));
			
			//comparamos las jugadas y devolvemos los ganadores
			session.setAttribute(CargaAtributos.ganadores, reglas.mejorJugada(jugadores));
			
			
		}
			
		
		
	}

}

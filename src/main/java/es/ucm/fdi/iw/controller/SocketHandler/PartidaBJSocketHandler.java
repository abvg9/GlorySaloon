package es.ucm.fdi.iw.controller.SocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import es.ucm.fdi.iw.common.enums.Movimientos;
import es.ucm.fdi.iw.games.barajas.Carta;
import es.ucm.fdi.iw.games.factorias.FactoriaJuego;
import es.ucm.fdi.iw.games.factorias.FactoriaMovimiento;
import es.ucm.fdi.iw.games.logica.Juego;
import es.ucm.fdi.iw.games.logica.Jugador;
import es.ucm.fdi.iw.games.logica.JugadorGenerico;
import es.ucm.fdi.iw.games.movimientos.Movimiento;
import es.ucm.fdi.iw.model.User;

public class PartidaBJSocketHandler extends PartidaSocketHandler {

	private static Logger log = Logger.getLogger(PartidaBJSocketHandler.class);
	
	private static final int cartasIniciales = 2;
				
	@Override
	synchronized public void handleTextMessage(WebSocketSession session, TextMessage movimiento) {
    	
		String userName = session.getPrincipal().getName();
		User u = encuentraUsuario(session);
		int i = buscaPartida(u.getId());
		int j = partidas.get(i).juego.getJugador(u.getLogin());
		
		Jugador jugador = partidas.get(i).juego.getJugadores().get(j);
		Partida p = partidas.get(i);
				
		Movimiento mov = null;
		Movimiento aux = null;
		
		boolean acabo = false;
		ArrayList<Jugador> ganadores = null;
		
		switch(movimiento.getPayload()) {
		
			case "aposto": 
						   double apostado = Double.parseDouble(movimiento.getPayload().split(" aposto ")[1]);
						   mov = FactoriaMovimiento.movimiento(Movimientos.apostar, apostado, jugador, p.juego.getBaraja());
						   try {
							   aux = mov.ejecutar(session);
							   partidas.get(i).juego.setTotalApostado(partidas.get(i).juego.getTotalApostado() + apostado);
						   } catch (IOException e) {
							   log.warn("No se pudo enviar el mensaje a " 
				    					+ session.getPrincipal().getName() + ":", e);
						   }
						   partidas.get(i).juego.getJugadores().set(j,aux.getJugador());
			               break;
			               
			case "pidio":  
						   mov = FactoriaMovimiento.movimiento(Movimientos.pedirCartas, 0.0, jugador, p.juego.getBaraja());
							try {
								aux = mov.ejecutar(session);
								partidas.get(i).juego.getJugadores().set(j,aux.getJugador());
								partidas.get(i).juego.setBaraja(aux.getBaraja());				
							} catch (IOException e) {
				    			log.warn("No se pudo enviar el mensaje a " 
				    					+ session.getPrincipal().getName() + ":", e);
							}
						   break;
						   
			case "paso":   
						   if(partidas.get(i).turno != partidas.get(i).jugadoresSk.size()) {
							   partidas.get(i).turno++;
							   mensajePrivado("turno",partidas.get(i).jugadoresSk.get(partidas.get(i).turno));
						   }else {
							   //partida acabada -> repartimos el botin y se reincia la partida
							   ganadores = partidas.get(i).juego.getReglas().mejorJugada(partidas.get(i).juego.getJugadores());					   
							   partidas.get(i).juego.repartirBotin(ganadores);
							   partidas.get(i).juego.setTotalApostado(0.0);
							   acabo = true;
						   }
						   break;
		}
		
		if(mov != null && !acabo) {
	    	log.info(userName+": "+movimiento.getPayload());
	    	broadcast(userName + ": " + movimiento.getPayload(),partidas.get(i).jugadoresSk);
		}
		
		if(acabo) {
			acabo = false;
			String cabezera = "server Se acabo la partida, gan";
			String mensaje = "";
			
			for(int k = 0; k < ganadores.size();k++) {
				mensaje += ganadores.get(k).getNombre() +", ";
			}
			
			if(ganadores.size() == 1) {
				broadcast(cabezera+"ó "+ ganadores.get(0).getNombre(),partidas.get(i).jugadoresSk);
			}else {
				broadcast(cabezera+"aron "+ mensaje,partidas.get(i).jugadoresSk);
			}
			
			//esperamos 10 segundos
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			partidas.get(i).juego.getBaraja().setCatasDadas(0);
			//repartimos de nuevo las cartas.
			partidas.get(i).juego.reparteCartas(cartasIniciales, partidas.get(i).juego.getJugadores().size());
			//se las mandamos
			mensaje = "";
			for(int l = 0; l < partidas.get(i).juego.getJugadores().size();l++) {
				
				for(int m = 0; m < partidas.get(i).juego.getJugadores().get(l).getMano().size();m++) {
					mensaje +="["+ partidas.get(i).juego.getJugadores().get(l).getMano().get(m).getPalo().toString() +"] ";
					mensaje +="["+ partidas.get(i).juego.getJugadores().get(l).getMano().get(m).getValor().toString()+"] ";
				}
				
				mensajePrivado("server mano "+mensaje,partidas.get(i).jugadoresSk.get(l));
			}
		}
    }
    
    @Override
    synchronized public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	super.afterConnectionEstablished(session);
    	
    	User u = encuentraUsuario(session);
    	JugadorGenerico jugador = new JugadorGenerico();
		jugador.setApostado(0.0);
		jugador.setDinero(u.getDinero());
		jugador.setMano(new ArrayList<Carta>());
		jugador.setNombre(u.getLogin());
		
    	String mensaje = "";
		int indice = buscaPartida(u.getId());
		
    	//si la partida no estaba creada la creamos y la añadimos.
		if(indice != -1) {
			
			ArrayList<Jugador> jugadores = new ArrayList<Jugador>(u.getPartida().getMaxJugadores());		
			
			Juego juego = FactoriaJuego.juego(u.getPartida().getJuego(), jugadores,u.getId());
			
			Partida p = new Partida(juego);
			p.turno = 0;
			p.jugadoresSk.add(session);
			p.juego.getBaraja().barajaCartas();
			p.juego.setTotalApostado(0.0);
			
			for(int i = 0; i < cartasIniciales;i++) {
				jugador.getMano().add(p.juego.getBaraja().daUnaCarta());
				jugador.getMano().add(p.juego.getBaraja().daUnaCarta());
		    }
			
			jugador.setTurno(0);
			jugadores.add(jugador);
			p.juego.setJugadores(jugadores);
			
			partidas.add(p);
			
			broadcast(session.getPrincipal().getName() + " se unio a la partida.",p.jugadoresSk);
			mensajePrivado("server turno",session);
			
		}else {
			
			for(int i = 0; i < cartasIniciales;i++) {
				jugador.getMano().add(partidas.get(indice).juego.getBaraja().daUnaCarta());
				jugador.getMano().add(partidas.get(indice).juego.getBaraja().daUnaCarta());
		    }
			
			partidas.get(indice).añadeJugador(jugador, session);
			broadcast(session.getPrincipal().getName() + " se unio a la partida.",partidas.get(indice).jugadoresSk);
		}

		log.info(session.getPrincipal().getName()+"se unio a la partida "+ u.getPartida().getId());
	}
    
    @Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		
		User u = encuentraUsuario(session);
		int i = buscaPartida(u.getId());	
		int j = partidas.get(i).juego.getJugador(u.getLogin());
		Jugador jugador = partidas.get(i).juego.getJugadores().get(j);
		
		double dinero = 0;
		
		dinero = jugador.getDinero() - u.getDinero();
		if(dinero > 0) {
			u.setPganadas(u.getPganadas()+1);
			u.setDganado(dinero);
		}else {
			u.setPperdidas(u.getPperdidas()+1);
			u.setDperdido(Math.abs(dinero));
		}
		
		u.setPjugadas(u.getPjugadas()+1);
		u.setDinero(jugador.getDinero());
		entityManager.remove(u);
			
		partidas.get(i).juego.getJugadores().remove(j);
		partidas.get(i).jugadoresSk.remove(session);
    	broadcast(session.getPrincipal().getName() + " salio de la partida.",partidas.get(i).jugadoresSk);	
    	log.info(session.getPrincipal().getName()+"salio de la partida "+ u.getPartida().getId());
	}

   
}

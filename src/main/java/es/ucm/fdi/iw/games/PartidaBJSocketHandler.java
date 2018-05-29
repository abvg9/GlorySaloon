package es.ucm.fdi.iw.games;

import java.util.ArrayList;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import es.ucm.fdi.iw.common.enums.Barajas;
import es.ucm.fdi.iw.common.enums.Respuesta;
import es.ucm.fdi.iw.model.User;

public class PartidaBJSocketHandler extends PartidaSocketHandler {
	
	public PartidaBJSocketHandler(){
		super(2);
	}
			
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage movimiento) {
    	
		Object[] indices = buscaIndicesPyJ(session);
		int indicePartida = (int)indices[1];
		int indiceJugador = (int)indices[2];		
		String mensaje[] = movimiento.getPayload().split(" ");
		
		switch(mensaje[1]) {
		
			case "apostó": 
				partidas.get(indicePartida).aposto(Double.valueOf(mensaje[2]), indiceJugador);
				broadcast(movimiento.getPayload(),partidas.get(indicePartida).jugadoresSk);
		        break;
			               
			case "pidió":  
				Respuesta resp = partidas.get(indicePartida).reparteAuno(1, indiceJugador);
				broadcast(movimiento.getPayload(),partidas.get(indicePartida).jugadoresSk);
				
				if(resp == Respuesta.NoQuedanCartas) {
					broadcast("sinCartas",partidas.get(indicePartida).jugadoresSk);
				}else {
					entregaCartasAUno(indicePartida,indiceJugador);
					if(resp == Respuesta.TePasaste) {
						mensajePrivado("tepasaste ",session);
						paso(indicePartida, session);
						broadcast(session.getPrincipal().getName()+" pasó de turno",partidas.get(indicePartida).jugadoresSk);
					}					
				}
			    break;
						   
			case "pasó":			
				paso(indicePartida, session);
				broadcast(movimiento.getPayload(),partidas.get(indicePartida).jugadoresSk);
			    break;
		}

	}
    
    @Override
    public synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	super.afterConnectionEstablished(session);
    	
		Object[] indices = buscaIndicesPyJ(session);
		User u = (User)indices[0];
		int indicePartida = (int)indices[1];
		
    	Jugador jugador = new Jugador(new ArrayList<Carta>(), u.getDinero(), u.getLogin(), 0.0);
				
		if(indicePartida == -1) {
			
			ArrayList<Jugador> jugadores = new ArrayList<Jugador>(u.getPartida().getMaxJugadores());
			jugadores.add(jugador);
			Juego juego = new Juego(u.getPartida().getJuego(), jugadores, Barajas.Francesa, u.getPartida().getMaxJugadores());
			
			Partida p = new Partida(juego,session,u.getPartida().getNombre());
			p.reiniciaPartida();
			partidas.add(p);
						
			broadcast(session.getPrincipal().getName() + " se unió a la partida.",p.jugadoresSk);
						
		}else {
			
			partidas.get(indicePartida).añadeJugador(jugador, session);
			broadcast(session.getPrincipal().getName() + " se unió a la partida." ,partidas.get(indicePartida).jugadoresSk);
			
			if(partidas.get(indicePartida).juego.getJugadores().size() == u.getPartida().getMaxJugadores()) {
				
				entregaCartasATodos(indicePartida,cartasIniciales);
				broadcast("empezo ",partidas.get(indicePartida).jugadoresSk);
				avisaTurno(indicePartida);
				log.info("La partida "+ u.getPartida().getNombre()+" ha comenzado");
			}
			
		}

		log.info(session.getPrincipal().getName()+" se unió a la partida "+ u.getPartida().getNombre()+".");
	}
    
    @Override
    public synchronized void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		
		Object[] indices = buscaIndicesPyJ(session);
		User u = (User)indices[0];
		int indicePartida = (int)indices[1];
		int indiceJugador = (int)indices[2];
		
		Jugador jugador = partidas.get(indicePartida).juego.getJugadores().get(indiceJugador);

		double dinero = 0;
		
		dinero = jugador.getDinero() - u.getDinero();
		if(dinero > 0) {
			u.setPganadas(u.getPganadas()+1);
			u.setDganado(dinero);
		}else {
			u.setPperdidas(u.getPperdidas()+1);
			u.setDperdido(jugador.getDinero() + u.getDinero());
		}
		
		u.setPjugadas(u.getPjugadas()+1);
		u.setDinero(jugador.getDinero());
		entityManager.persist(entityManager.merge(u));
			
		partidas.get(indicePartida).juego.getJugadores().remove(indiceJugador);
		partidas.get(indicePartida).jugadoresSk.remove(session);
    	broadcast("salio "+session.getPrincipal().getName(),partidas.get(indicePartida).jugadoresSk);

    	if(partidas.get(indicePartida).jugadoresSk.size() == 0) {
    		partidas.remove(indicePartida);
    		entityManager.remove(u.getPartida());
    	}
    	
		if(indiceJugador == partidas.get(indicePartida).juego.getTurno()) {
			if(salio(indicePartida)) {
				broadcast("fin",partidas.get(indicePartida).jugadoresSk);
			}
		}
		
		log.info(session.getPrincipal().getName()+" salió de la partida."+ u.getPartida().getNombre());
	}
       
}

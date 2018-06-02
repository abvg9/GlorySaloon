package es.ucm.fdi.iw.games;

import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import es.ucm.fdi.iw.common.enums.Barajas;
import es.ucm.fdi.iw.common.enums.Juegos;
import es.ucm.fdi.iw.common.enums.Respuesta;

public class PartidaBJSocketHandler extends PartidaSocketHandler {
	
	public PartidaBJSocketHandler(){
		super(2);
	}
			
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage movimiento) {
    	
		int[] indices;
		int indicePartida;
		int indiceJugador;	
		String mensaje[] = movimiento.getPayload().split(" ");
		
		switch(mensaje[1]) {
		
			case "sinblanca":
				indices = buscaIndicesPyJ(session.getPrincipal().getName());
				indicePartida = indices[0];
				indiceJugador = indices[1];
				partidas.get(indicePartida).donaAjugador(indiceJugador, Integer.valueOf(mensaje[2]));
				break;
		
			case "apostó": 
				indices = buscaIndicesPyJ(session.getPrincipal().getName());
				indicePartida = indices[0];
				indiceJugador = indices[1];
				partidas.get(indicePartida).aposto(Integer.valueOf(mensaje[2]), indiceJugador);
				broadcast(movimiento.getPayload(),partidas.get(indicePartida).getJugadoresSk());
		        break;
			               
			case "pidió": 
				indices = buscaIndicesPyJ(session.getPrincipal().getName());
				indicePartida = indices[0];
				indiceJugador = indices[1];
				Respuesta resp = partidas.get(indicePartida).reparteAuno(1, indiceJugador);
				broadcast(movimiento.getPayload(),partidas.get(indicePartida).getJugadoresSk());
				
				if(resp == Respuesta.NoQuedanCartas) {
					broadcast("sinCartas",partidas.get(indicePartida).getJugadoresSk());
				}else {
					entregaCartasAUno(indicePartida,indiceJugador);
					if(resp == Respuesta.TePasaste) {
						mensajePrivado("tepasaste ",session);
						paso(indicePartida, session);
						broadcast(session.getPrincipal().getName()+" pasó de turno",partidas.get(indicePartida).getJugadoresSk());
					}					
				}
			    break;
						   
			case "pasó":
				indices = buscaIndicesPyJ(session.getPrincipal().getName());
				indicePartida = indices[0];
				indiceJugador = indices[1];
				if(paso(indicePartida, session)) {
					broadcast(movimiento.getPayload(),partidas.get(indicePartida).getJugadoresSk());
				}
			    break;
			
			case "entro":
				//usuario(0) entro(1) dinero(2) maxJugadores(3) tipoDeJuego(4) nombrePartida(5)
				Jugador jugador = new Jugador(new ArrayList<Carta>(), Integer.valueOf(mensaje[2]), session.getPrincipal().getName(), 0);
				
				indicePartida = buscaPartida(mensaje[5]);
				
				if(indicePartida == -1) {
			
					ArrayList<Jugador> jugadores = new ArrayList<Jugador>(Integer.valueOf(mensaje[3]));
					jugadores.add(jugador);
					Juego juego = new Juego(Juegos.valueOf(mensaje[4]), jugadores, Barajas.Francesa, Integer.valueOf(mensaje[3]));
					
					Partida p = new Partida(juego,session,mensaje[5]);
					p.reiniciaPartida();
					partidas.add(p);
								
					broadcast(mensaje[0] + " se unió a la partida.",p.getJugadoresSk());
					log.info("Se creo una partida");		
				}else {
					
					partidas.get(indicePartida).añadeJugador(jugador, session);
					broadcast(mensaje[0] + " se unió a la partida." ,partidas.get(indicePartida).getJugadoresSk());
					
					if(partidas.get(indicePartida).getJuego().getJugadores().size() == Integer.valueOf(mensaje[3])) {
						
						entregaCartasATodos(indicePartida,cartasIniciales);
						broadcast("empezo ",partidas.get(indicePartida).getJugadoresSk());
						avisaTurno(indicePartida);
						log.info("La partida "+ partidas.get(indicePartida).getNombre()+" ha comenzado");
					}
					
				}

				log.info(mensaje[0]+" se unió a la partida "+ mensaje[5]+".");
				break;
		}

	}
    
    @Override
    public  void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	super.afterConnectionEstablished(session);
    	   	
		log.info(session.getPrincipal().getName()+" se ha conectado.");
	}
    
    @Transactional
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		
		int[] indices = buscaIndicesPyJ(session.getPrincipal().getName());
		int indicePartida = indices[0];
		int indiceJugador = indices[1];
		
		Jugador jugador = partidas.get(indicePartida).getJuego().getJugadores().get(indiceJugador);
	
		log.info(session.getPrincipal().getName()+" salió de la partida."+ partidas.get(indicePartida).getNombre());
				
    	if(partidas.get(indicePartida).getJugadoresSk().size() == 1) {
    		partidas.get(indicePartida).setJugadoresSk(null);
    		partidas.get(indicePartida).getJuego().setJugadores(null);
    		partidas.remove(indicePartida);
    	}else if (salioYtermino(indicePartida,indiceJugador)) {
    		hayJugadores = false;
    		broadcast("salio "+jugador.getNombre(),partidas.get(indicePartida).getJugadoresSk());		
			broadcast("fin",partidas.get(indicePartida).getJugadoresSk());
    	}

	}
       
}

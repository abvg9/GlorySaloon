package es.ucm.fdi.iw.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatSocketHandler extends TextWebSocketHandler {

	private static Logger log = Logger.getLogger(ChatSocketHandler.class);
	
	private static final List<WebSocketSession> chatters = new CopyOnWriteArrayList<>();
	
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
    	String userName = session.getPrincipal().getName();
    	log.info("mensaje recibo: " 
    			+ message.getPayload() + " de " 
    			+ userName);
    	broadcast(userName + ": " + message.getPayload());
    }
    
    @Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	super.afterConnectionEstablished(session);
    	session.sendMessage(new TextMessage("Bienvenido al chat; hay " + (1+chatters.size()) + " personas en el chat."));
    	chatters.add(session);
    	broadcast(session.getPrincipal().getName() + " entro al chat!");
	}
    
    @Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
    	chatters.remove(session);
    	broadcast(session.getPrincipal().getName() + " se fue.");
	}

	private void broadcast(String message) {
		log.info("broadcasting: " + message);
    	for (WebSocketSession s : chatters) {
    		try {
    			s.sendMessage(new TextMessage(message));
    		} catch (Exception e) {
    			log.warn("No se pudo enviar el mensaje a " 
    					+ s.getPrincipal().getName() + ":", e);
    		}
    	}
    }
}

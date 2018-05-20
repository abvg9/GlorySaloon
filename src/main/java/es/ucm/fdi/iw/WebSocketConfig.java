package es.ucm.fdi.iw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import es.ucm.fdi.iw.common.utils.CargaAtributos;
import es.ucm.fdi.iw.controller.SocketHandler.ChatSocketHandler;
import es.ucm.fdi.iw.controller.SocketHandler.PartidaBJSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    	
        registry.addHandler(chatHandler(), "/"+CargaAtributos.chatSocket)
        	.addInterceptors(new HttpSessionHandshakeInterceptor());
        
        registry.addHandler(partidaHandler(), "/"+CargaAtributos.partidaSocket)
    	.addInterceptors(new HttpSessionHandshakeInterceptor());
    }

    @Bean
    public WebSocketHandler chatHandler() {
        return new ChatSocketHandler();
    }
    
    @Bean
    public WebSocketHandler partidaHandler() {
        return new PartidaBJSocketHandler();
    }
}
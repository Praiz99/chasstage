package com.wckj.chasstage.api.server.release.ws.config;

import com.wckj.chasstage.api.server.release.ws.handler.SpringWebSocketHandler;
import com.wckj.chasstage.api.server.release.ws.interceptor.SpringWebSocketHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Configuration
@EnableWebSocket
public class SpringWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(),"/socketServer/websocket").setAllowedOrigins("*")
                .addInterceptors(new SpringWebSocketHandlerInterceptor());
        
        registry.addHandler(webSocketHandler(), "/socketServer/sockjs").setAllowedOrigins("*")
               .addInterceptors(new SpringWebSocketHandlerInterceptor()).withSockJS();

        registry.addHandler(webSocketHandler(),"/socketServer/JhWebsocket").setAllowedOrigins("*")
                .addInterceptors(new SpringWebSocketHandlerInterceptor());
        
        registry.addHandler(webSocketHandler(),"/socketServer/SxWebsocket").setAllowedOrigins("*")
        		.addInterceptors(new SpringWebSocketHandlerInterceptor());
    }
 
    @Bean
    public TextWebSocketHandler webSocketHandler(){
 
        return new SpringWebSocketHandler();
    }

}

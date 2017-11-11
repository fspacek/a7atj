package cz.edhouse.javaee.rest;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Frantisek Spacek
 */
@ServerEndpoint("/chat")
public class WebSocketsEndpoint {

    @OnMessage
    public String onMessage(String message) {
        return String.format("Server recieved %s", message);
    }
    
}


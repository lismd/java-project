package com.honeywell.avionics.browser.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/socket/flight/{flightId}")
@Component
public class SocketServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);

    private static AtomicInteger connectedCount = new AtomicInteger(0);

    private Session session;

    private String flightId;

    @OnOpen
    public void onOpen(Session session, @PathParam("flightId") String flightId) {
        this.session = session;
        this.flightId = flightId;
        SocketServerClientsContainer.add(flightId, this);
        connectedCount.addAndGet(1);
    }

    @OnClose
    public void onClose() {
        SocketServerClientsContainer.remove(flightId, this);
    }

    @OnError
    public void onError(Session session, Throwable error) {

    }

    public void sendMessage(String flightData) {
        try {
            this.session.getBasicRemote().sendText(flightData);
        } catch (IOException e) {
            LOGGER.error("sendMessage exception. data = {}", flightData, e);
        }
    }
}

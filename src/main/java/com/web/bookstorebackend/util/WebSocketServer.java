package com.web.bookstorebackend.util;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/order/{userId}")
@Component
public class WebSocketServer {

    private static final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap<>();

    public void sendMessage(Session toSession, String message) {
        if (toSession != null) {
            try {
                toSession.getBasicRemote().sendText(message);
                System.out.println("send message " + message + " success");
            } catch (IOException e) {
                System.out.println("send message failed");
            }
        } else {
            System.out.println("session is null");
        }
    }

    public void sendMessageToUser(String user, String message) throws InterruptedException {
        System.out.println("send message to user: " + user);
        Session toSession = SESSIONS.get(user);
        sendMessage(toSession, message);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("receive message: " + message);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        if (SESSIONS.get(userId) != null) {
            return;
        }
        SESSIONS.put(userId, session);
        System.out.println("user " + userId + " connected");
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        SESSIONS.remove(userId);
        System.out.println("user " + userId + " disconnected");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("error");
    }

}

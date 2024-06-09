package com.ftn.sbnz.ws;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.ftn.sbnz.dtos.MessageDTO;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class SocketHandler extends TextWebSocketHandler {

    public static final List<WebSocketSession> SESSIONS = new ArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {
        // TODO
        System.out.println("Message received: " + message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        SESSIONS.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        SESSIONS.remove(session);
    }

    public static void sendMessage(MessageDTO message) {
        System.out.println("aaa");
        String alarmMessage = new Gson().toJson(message);
        for (WebSocketSession session : SESSIONS) {
            try {
                session.sendMessage(new TextMessage(alarmMessage));
            } catch (IOException e) {
                try {
                    session.close();
                    SESSIONS.clear();
                } catch (IOException e1) {
                    System.out.println(message + " was not sent");
                }
            }
        }
    }
}

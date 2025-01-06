package com.ssafy.live_service.api.service.live.vo;

import com.ssafy.live_service.common.exception.AppException;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Sessions {

    private final List<WebSocketSession> values;

    private Sessions(List<WebSocketSession> values) {
        this.values = values;
    }

    private static Sessions of(List<WebSocketSession> values) {
        return new Sessions(values);
    }

    public static Sessions create() {
        return of(new ArrayList<>());
    }

    public void add(WebSocketSession session) {
        values.add(session);
    }

    public void remove(WebSocketSession session) {
        Optional<WebSocketSession> findSession = values.stream()
            .filter(s -> s.getId().equals(session.getId()))
            .findFirst();
        if (findSession.isEmpty()) {
            return;
        }
        values.remove(findSession.get());
    }

    public void sendMessage(String msg) {
        values.forEach(session -> {
            try {
                session.sendMessage(new TextMessage(msg));
            } catch (IOException e) {
                throw new AppException(e);
            }
        });
    }
}

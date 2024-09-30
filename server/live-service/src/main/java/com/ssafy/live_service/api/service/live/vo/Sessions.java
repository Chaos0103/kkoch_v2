package com.ssafy.live_service.api.service.live.vo;

import com.ssafy.live_service.common.exception.AppException;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Sessions {

    private final List<SessionInfo> values;

    private Sessions(List<SessionInfo> values) {
        this.values = values;
    }

    private static Sessions of(List<SessionInfo> values) {
        return new Sessions(values);
    }

    public static Sessions create() {
        return of(new ArrayList<>());
    }

    public void add(WebSocketSession session) {
        SessionInfo sessionInfo = SessionInfo.of(session);
        values.add(sessionInfo);
    }

    public void remove(String sessionId) {
        Optional<SessionInfo> findSessionInfo = values.stream()
            .filter(sessionInfo -> sessionInfo.eqSessionId(sessionId))
            .findFirst();
        if (findSessionInfo.isEmpty()) {
            return;
        }
        values.remove(findSessionInfo.get());
    }

    public void sendMessage(String msg) {
        values.stream()
            .map(SessionInfo::getSession)
            .forEach(session -> {
                    try {
                        session.sendMessage(new TextMessage(msg));
                    } catch (IOException e) {
                        throw new AppException(e);
                    }
                }
            );
    }
}

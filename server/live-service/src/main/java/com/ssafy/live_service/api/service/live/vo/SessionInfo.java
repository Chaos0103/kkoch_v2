package com.ssafy.live_service.api.service.live.vo;

import com.ssafy.live_service.common.exception.AppException;
import lombok.Getter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Objects;

@Getter
public class SessionInfo {

    private final String sessionId;
    private final WebSocketSession session;

    private SessionInfo(WebSocketSession session) {
        this.sessionId = session.getId();
        this.session = session;
    }

    public static SessionInfo of(WebSocketSession session) {
        return new SessionInfo(session);
    }



    public void sendMessage(String json) {
        try {
            session.sendMessage(new TextMessage(json));
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    public boolean eqSessionId(String sessionId) {
        return this.sessionId.equals(sessionId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionInfo that = (SessionInfo) o;
        return Objects.equals(getSessionId(), that.getSessionId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSessionId());
    }
}

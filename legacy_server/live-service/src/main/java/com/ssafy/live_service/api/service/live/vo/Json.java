package com.ssafy.live_service.api.service.live.vo;

import com.ssafy.live_service.api.service.live.Command;
import com.ssafy.live_service.api.service.live.Role;
import com.ssafy.live_service.common.exception.AppException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Json {

    private static final String ROLE = "role";
    private static final String CMD = "command";
    private static final String VIDEO_SESSION_ID = "sessionId";

    private final JSONObject obj;

    private Json(JSONObject obj) {
        this.obj = obj;
    }

    public static Json of(String str) {
        return new Json(parser(str));
    }

    public Role getRole() {
        String role = get(ROLE);
        return Role.valueOf(role);
    }

    public Command getCmd() {
        String cmd = get(CMD);
        return Command.valueOf(cmd);
    }

    public String getVideoSessionId() {
        return get(VIDEO_SESSION_ID);
    }

    private String get(String key) {
        return (String) obj.get(key);
    }

    private static JSONObject parser(String str) {
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(str);
        } catch (ParseException e) {
            throw new AppException(e);
        }
    }
}

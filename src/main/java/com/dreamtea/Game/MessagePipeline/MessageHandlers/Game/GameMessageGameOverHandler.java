package com.dreamtea.Game.MessagePipeline.MessageHandlers.Game;

import com.dreamtea.Game.GroundServer.Service.RoomService;
import com.dreamtea.Game.MessagePipeline.MessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameMessageGameOverHandler extends MessageHandler {
    private static final String[] SUPPORTEDTYPES = {"GAMEOVER"};

    @Autowired
    private RoomService roomService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String handle(Map<String, Object> messages) throws Exception {
        String type = ((String) messages.get("type"));

        if(isSupported(SUPPORTEDTYPES, type)) {
            int roomId = ((int) messages.get("roomId"));
//            long timestamp = ((long) messages.get("timestamp"));
            String playerName = ((String) messages.get("playerName"));

            if(roomService.getRoomStatus(roomId)) {
                /*
                可能存在一种情况:已经有一名玩家完成了游戏,而此时有另一名玩家在很短的时间内同时发出了一个请求
                在很短的时间内,可能就出现一种情形:两个人都发送了一条GameOver信息,而只有当前端接收到了GameOverMessage之后
                才会把room的状态修改到结束。所以在这里必须直接把状态清空到Over
                */

                roomService.delReadyAll(roomId);
                Map<String, String> map = new HashMap<>();
                map.put("type", "GAMEOVER");
                map.put("message", playerName);

                roomService.setOver(roomId);
                return objectMapper.writeValueAsString(map);
            }
        }

        return nextHandler.handle(messages);
    }
}

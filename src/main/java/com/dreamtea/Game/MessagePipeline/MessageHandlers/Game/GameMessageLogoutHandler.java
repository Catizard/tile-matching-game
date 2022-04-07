package com.dreamtea.Game.MessagePipeline.MessageHandlers.Game;

import com.dreamtea.Boot.Service.RedisService;
import com.dreamtea.Game.GroundServer.Service.RoomService;
import com.dreamtea.Game.MessagePipeline.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.dreamtea.Boot.Configurer.WebConfigurer.REDIS_REMOTETOKEN_PREFIX;
import static com.dreamtea.Boot.Configurer.WebConfigurer.REDIS_SPLIT_SYMBOL;

@Component
public class GameMessageLogoutHandler extends MessageHandler {
    private static final String[] SUPPORTEDTYPES = {"LOGOUT"};

    @Autowired
    private RoomService roomservice;

    @Autowired
    private RedisService redisService;

    @Override
    public String handle(Map<String, Object> messages) throws Exception {
        String type = ((String) messages.get("type"));

        if(isSupported(SUPPORTEDTYPES, type)) {
            int roomId = ((int) messages.get("roomId"));

            /*TODO 删除player
            这里有一个很不可控的问题，因为房间列表的消息管道和游戏房间内的消息管道隔离了
                    所以可能存在竟态关系
            Ground的消息管道中接收到一个logout事件的时候会删除指定的player
            而如果Ground先接收到一个信息，那么这里获取到的成员个数就是1
            反过来如果是Game先接收到一个信息，那么这里获取到的成员个数是2
                    因此可能需要去掉Ground的消息管道
            避免竟态事件的引发
            */
            String remoteToken = ((String) messages.get("remoteToken")).split(REDIS_SPLIT_SYMBOL)[1];
            roomservice.delMemberInRoom(remoteToken, roomId);

            int isReady = ((int) messages.get("isReady"));
            if(isReady != 0) {
                roomservice.delReadyPlayer(roomId);
            }

            if(roomservice.getMemberCountInRoom(roomId) == 1) {
                //此时游戏直接结束,把信息转型成一个GameOver message由GameMessageGameOverHandler处理，向前台发送游戏结束信息
                //但是此时游戏中剩下的玩家才是胜者，所以需要在这里修改playerName为剩下的玩家
                String winnerToken = REDIS_REMOTETOKEN_PREFIX + roomservice.getMemberListInRoom(roomId).get(0);
                String winnerName = ((String) redisService.get(winnerToken)).split(REDIS_SPLIT_SYMBOL)[1];
                System.out.println(winnerName);

                //转型成GameOver事件,等待GameOver处理器发送信息
                messages.put("type", "GAMEOVER");
                messages.put("playerName", winnerName);
            }
        }

        return nextHandler.handle(messages);
    }
}

package com.dreamtea.Boot.Controller;

import com.dreamtea.Boot.Service.RedisService;
import com.dreamtea.Game.GroundServer.Service.GameService;
import com.dreamtea.Game.GroundServer.Service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static com.dreamtea.Boot.Configurer.WebConfigurer.REDIS_MAP_PREFIX;
import static com.dreamtea.Boot.Configurer.WebConfigurer.REDIS_SPLIT_SYMBOL;

@RestController
public class ActionController {
    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private GameService gameService;

    @PostMapping("/addBlock")
    public String addBlock(@RequestParam("inHand") int inHand, @RequestParam("remoteToken") String remoteToken, @RequestParam("blockId") int blockId) throws JsonProcessingException {
        if(inHand == -1 || blockId == -1) {
            return "NO";
        }
        remoteToken = remoteToken.split(REDIS_SPLIT_SYMBOL)[1];
        String keyToken = REDIS_MAP_PREFIX + remoteToken;
        ArrayList<Integer> map = (ArrayList<Integer>) redisService.get(keyToken);

        int num = gameService.tryAddBlock(map, inHand, blockId);
        if(num == -1 || num != inHand) {
            return "NO";
        }

        map.set(inHand, 0);
        map.set(blockId, 0);

        redisService.set(keyToken, map);

        int total = 0;
        for (Integer val : map) {
            if(val != 0) {
                ++total;
            }
        }

        if(total == 0) {
            return "GAMEOVER";
        }
        return "OK";
    }

    @GetMapping("/getRoomList")
    public String getRoomList() throws JsonProcessingException {
        return objectMapper.writeValueAsString(roomService.getRoomMemberNameList());
    }

    @GetMapping("/getRoomStatusList")
    public String getRoomStatusList() throws JsonProcessingException {
        return objectMapper.writeValueAsString(roomService.getRoomStatusList());
    }

    @GetMapping("/getToken")
    public String getToken(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("token");
    }

    @GetMapping("/getReady")
    public String getReady(@RequestParam("roomId") int roomId) {
        ArrayList<ArrayList<String>> roomList = roomService.getRoomMemberNameList();
        ArrayList<Integer> roomReadyCountList = roomService.getRoomReadyCountList();

        roomService.addReadyPlayer(roomId);
        int hasPlayer = roomList.get(roomId).size();
        int readyPlayer = roomReadyCountList.get(roomId);

        if(hasPlayer == readyPlayer && hasPlayer >= 2) {
            return "READY";
        } else {
            return "NO";
        }
    }
}

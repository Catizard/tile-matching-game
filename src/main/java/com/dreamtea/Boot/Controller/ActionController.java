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
        System.out.println(inHand);
        System.out.println(blockId);
        if(inHand == -1 || blockId == -1) {
            return "NO";
        }
        //TODO 由于 token 分配的问题,这里可能还是会出现找到同一个map的问题
        String keyToken = "map-" + remoteToken;
        ArrayList<Integer> map = (ArrayList<Integer>) redisService.get(keyToken);

        int num = gameService.tryAddBlock(map, inHand, blockId);
        if(num == -1 || num != inHand) {
            return "NO";
        }

        //TODO 此处修改map的操作有些耦合
        map.set(inHand, 0);
        map.set(blockId, 0);
        return "OK";
    }

    @GetMapping("/getRoomList")
    public String getRoomList() throws JsonProcessingException {
        return objectMapper.writeValueAsString(roomService.getRoomList());
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
        ArrayList<ArrayList<String>> roomList = roomService.getRoomList();
        ArrayList<Integer> roomReadyCountList = roomService.getRoomReadyCountList();

        //TODO roomId 的 api 需要统一
        --roomId;

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

package com.dreamtea.Boot.Controller;

import com.dreamtea.Boot.Domain.World;
import com.dreamtea.Boot.Service.RedisService;
import com.dreamtea.Game.GroundServer.Service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
public class ActionController {
    @Autowired
    RedisService redisService;

    @Autowired
    World world;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RoomService roomService;

    @GetMapping("/addBlock")
    public String addBlock(@RequestParam("blockId") int id) throws JsonProcessingException {
        int prevInhand = world.getInhand();
        int num = world.tryAddBlock(id);
        if(num == -1 || num != prevInhand) {
            return "[]";
        }
        return "[" + id + "," + num + "]";
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

        int hasPlayer = roomList.get(roomId).size();
        int readyPlayer = roomReadyCountList.get(roomId);

        //TODO 实际游戏中至少需要两名玩家才能开启,这里为了测试没有添加限制
        if(hasPlayer == readyPlayer) {
            return "READY";
        } else {
            return "NO";
        }
    }
}

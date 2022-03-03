package com.dreamtea.Game.GroundServer.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RoomServiceImpl implements RoomService {
    //TODO 初始大小 10 是在这里写死的 页面也是 以后或许需要调整一下?

    @Autowired
    @Qualifier("roomList")
    ArrayList<ArrayList<String>> roomList;

    @Autowired
    @Qualifier("roomStatusList")
    ArrayList<Boolean> roomStatusList;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public ArrayList<ArrayList<String>> getRoomList() {
        return roomList;
    }

    @Override
    public ArrayList<Boolean> getRoomStatusList() {
        return roomStatusList;
    }

    @Override
    public void add(String token, int roomId) {
        roomList.get(roomId).add(token);
    }

    @Override
    public void del(String token, int roomId) {
        roomList.get(roomId).remove(token);
    }
}

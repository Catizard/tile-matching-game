package com.dreamtea.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RoomServiceImpl implements RoomService{
    //TODO 初始大小 10 是在这里写死的 页面也是 以后或许需要调整一下?

    @Autowired
    @Qualifier("roomList")
    ArrayList<ArrayList<String>> roomList;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public ArrayList<ArrayList<String>> getRoomList() {
        return roomList;
    }
}

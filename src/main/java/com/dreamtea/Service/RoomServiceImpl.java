package com.dreamtea.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RoomServiceImpl implements RoomService{
    @Autowired
    ArrayList<ArrayList<String>> roomList;


    @Override
    public ArrayList<ArrayList<String>> getRoomList() {
        return roomList;
    }
}

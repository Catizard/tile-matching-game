package com.dreamtea.Game.GroundServer.Service;

import java.util.ArrayList;

public interface RoomService {
    ArrayList<ArrayList<String>> getRoomList();
    void add(String token, int roomId);
    void del(String token, int roomId);
}

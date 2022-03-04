package com.dreamtea.Game.GroundServer.Service;

import java.util.ArrayList;

public interface RoomService {
    ArrayList<ArrayList<String>> getRoomList();
    ArrayList<Boolean> getRoomStatusList();
    ArrayList<Integer> getRoomReadyCountList();
    void add(String token, int roomId);
    void del(String token, int roomId);
    void setRunning(int roomId);
    void setOver(int roomId);
    void addReadyPlayer(int roomId);
    void delReadyPlayer(int roomId);
    void delReadyAll(int roomId);
}

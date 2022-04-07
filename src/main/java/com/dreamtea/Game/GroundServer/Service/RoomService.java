package com.dreamtea.Game.GroundServer.Service;

import java.util.ArrayList;

public interface RoomService {
    ArrayList<ArrayList<String>> getRoomMemberList();
    ArrayList<Boolean> getRoomStatusList();
    ArrayList<Integer> getRoomReadyCountList();
    ArrayList<String> getMemberListInRoom(int roomId);
    void addMemberInRoom(String token, int roomId);
    void delMemberInRoom(String token, int roomId);
    void setRunning(int roomId);
    void setOver(int roomId);
    void addReadyPlayer(int roomId);
    void delReadyPlayer(int roomId);
    void delReadyAll(int roomId);
    int getMemberCountInRoom(int roomId);
    boolean getRoomStatus(int roomId);

}

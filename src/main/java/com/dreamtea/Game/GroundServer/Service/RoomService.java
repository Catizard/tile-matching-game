package com.dreamtea.Game.GroundServer.Service;

import java.util.ArrayList;

public interface RoomService {
    ArrayList<ArrayList<String>> getRoomMemberTokenList();

    ArrayList<ArrayList<String>> getRoomMemberNameList();

    ArrayList<Boolean> getRoomStatusList();

    ArrayList<Integer> getRoomReadyCountList();

    ArrayList<String> getRoomMemberTokenListInRoom(int roomId);

    ArrayList<String> getRoomMemberNameListInRoom(int roomId);

    void addMemberInRoom(String token, String name, int roomId);

    void delMemberInRoom(String token, String name, int roomId);

    void addMemberInRoomByToken(String token, int roomId);

    void delMemberInRoomByToken(String token, int roomId);

    void addMemberInRoomByName(String name, int roomId);

    void delMemberInRoomByName(String name, int roomId);

    void setRunning(int roomId);

    void setOver(int roomId);

    void addReadyPlayer(int roomId);

    void delReadyPlayer(int roomId);

    void delReadyAll(int roomId);

    int getMemberCountInRoom(int roomId);

    boolean getRoomStatus(int roomId);
}

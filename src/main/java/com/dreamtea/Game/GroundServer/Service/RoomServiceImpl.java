package com.dreamtea.Game.GroundServer.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.dreamtea.Game.Configurer.GameConfig.CHOOSEABLE_ROOM_COUNT;
import static com.dreamtea.Game.Configurer.GameConfig.MAX_PALYER_COUNT;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    ObjectMapper objectMapper;

    private final ArrayList<ArrayList<String>> roomTokenList;
    private final ArrayList<ArrayList<String>> roomNameList;
    private final ArrayList<Integer> roomReadyCountList;
    private final ArrayList<Boolean> roomStatusList;

    public RoomServiceImpl() {
        roomTokenList = new ArrayList<>();
        roomNameList = new ArrayList<>();
        roomReadyCountList = new ArrayList<>(CHOOSEABLE_ROOM_COUNT);
        for (int i = 0; i < CHOOSEABLE_ROOM_COUNT; ++i) {
            roomReadyCountList.add(0);
        }
        roomStatusList = new ArrayList<>(MAX_PALYER_COUNT);
        for (int i = 0; i < MAX_PALYER_COUNT; ++i) {
            roomStatusList.add(false);
        }

        for (int i = 0; i < CHOOSEABLE_ROOM_COUNT; ++i) {
            roomTokenList.add(new ArrayList<>(MAX_PALYER_COUNT));
            roomNameList.add(new ArrayList<>(MAX_PALYER_COUNT));
        }
    }

    @Override
    public ArrayList<ArrayList<String>> getRoomMemberTokenList() {
        return roomTokenList;
    }

    @Override
    public ArrayList<ArrayList<String>> getRoomMemberNameList() {
        return roomNameList;
    }

    @Override
    public ArrayList<Boolean> getRoomStatusList() {
        return roomStatusList;
    }

    @Override
    public ArrayList<Integer> getRoomReadyCountList() {
        return roomReadyCountList;
    }

    @Override
    public ArrayList<String> getRoomMemberTokenListInRoom(int roomId) {
        return roomTokenList.get(roomId);
    }

    @Override
    public ArrayList<String> getRoomMemberNameListInRoom(int roomId) {
        return roomNameList.get(roomId);
    }

    @Override
    public void addMemberInRoom(String token, String name, int roomId) {
        roomTokenList.get(roomId).add(token);
        roomNameList.get(roomId).add(name);
    }

    @Override
    public void delMemberInRoom(String token, String name, int roomId) {
        roomTokenList.get(roomId).remove(token);
        roomNameList.get(roomId).remove(name);
    }

    @Override
    public void addMemberInRoomByToken(String token, int roomId) {
        roomTokenList.get(roomId).add(token);
    }

    @Override
    public void delMemberInRoomByToken(String token, int roomId) {
        roomTokenList.get(roomId).remove(token);
    }

    @Override
    public void addMemberInRoomByName(String name, int roomId) {
        roomNameList.get(roomId).add(name);
    }

    @Override
    public void delMemberInRoomByName(String name, int roomId) {
        roomNameList.get(roomId).remove(name);
    }

    @Override
    public void setRunning(int roomId) {
        roomStatusList.set(roomId, true);
    }

    @Override
    public void setOver(int roomId) {
        roomStatusList.set(roomId, false);
    }

    @Override
    public void addReadyPlayer(int roomId) {
        int prevCount = roomReadyCountList.get(roomId);
        if (prevCount == MAX_PALYER_COUNT) {
            throw new IllegalArgumentException("Room player count out of range");
        }
        roomReadyCountList.set(roomId, prevCount + 1);
    }

    @Override
    public void delReadyPlayer(int roomId) {
        int prevCount = roomReadyCountList.get(roomId);
        if (prevCount == 0) {
            throw new IllegalArgumentException("Room player count out of range");
        }
        roomReadyCountList.set(roomId, prevCount - 1);
    }

    @Override
    public void delReadyAll(int roomId) {
        roomReadyCountList.set(roomId, 0);
    }

    @Override
    public int getMemberCountInRoom(int roomId) {
        return roomTokenList.get(roomId).size();
    }

    @Override
    public boolean getRoomStatus(int roomId) {
        return roomStatusList.get(roomId);
    }
}

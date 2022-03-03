package com.dreamtea.Game.Configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class RoomConfigurer {

    @Bean("roomList")
    public ArrayList<ArrayList<String>> roomListFactory() {
        ArrayList<ArrayList<String>> roomList = new ArrayList<>(10);
        for (int i = 0;i < 10;++i) {
            roomList.add(new ArrayList<>(6));
        }
        return roomList;
    }

    @Bean("roomStatusList")
    public ArrayList<Boolean> roomStatusList() {
        ArrayList<Boolean> roomStatusList = new ArrayList<>();
        for (int i = 0;i < 10;++i) {
            roomStatusList.add(true);
        }
        return roomStatusList;
    }
}

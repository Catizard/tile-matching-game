package com.dreamtea.Boot.Configurer;

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
}

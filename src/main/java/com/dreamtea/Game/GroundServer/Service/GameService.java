package com.dreamtea.Game.GroundServer.Service;

import java.util.ArrayList;

public interface GameService {
    int tryAddBlock(ArrayList<Integer> map, int inHand, int num);
    ArrayList<Integer> genMap(String fileName);
}

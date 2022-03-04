package com.dreamtea.Game.GroundServer.Service;

import com.dreamtea.Game.Utils.BlockUtil;
import com.dreamtea.Game.Utils.MapUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GameServiceImpl implements GameService {
    @Override
    public int tryAddBlock(ArrayList<Integer> map, int inHand, int num) {
        if(inHand == -1 || inHand == num) {
            return -1;
        }
        if(BlockUtil.check(map, inHand, num)) {
            return inHand;
        } else {
            return num;
        }
    }

    @Override
    public ArrayList<Integer> genMap(String fileName) {
        return MapUtil.genMap(fileName);
    }
}

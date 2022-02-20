package com.dreamtea.Domain;

import com.dreamtea.Utils.BlockUtil;
import com.dreamtea.Utils.MapUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class World {
    //TODO map cant be final ,user may use the random button
    private ArrayList<Integer> map;
    int inhand;

    public World() {
        map = new ArrayList<>();
        inhand = -1;
    }

    public int getInhand() {
        return inhand;
    }

    public int tryAddBlock(int num) {
        if(inhand == -1) {
            inhand = num;
            return -1;
        } else if(inhand == num) {
            inhand = -1;
            return -1;
        }
        if(BlockUtil.check(map, inhand, num)) {
            map.set(num, 0);
            map.set(inhand, 0);
            int result = inhand;
            inhand = -1;
            return result;
        } else {
            inhand = num;
            return num;
        }
    }

    public ArrayList<Integer> genMap(String fileName) {
        map = MapUtil.genMap(fileName);
        inhand = -1;
        return map;
    }
}

package com.dreamtea.Domain;

import com.dreamtea.Utils.BlockUtil;
import com.dreamtea.Utils.MapUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class World {
    //TODO map cant be final ,user may use the random button
    private ArrayList<Integer> map;
    private ArrayList<Integer> stk;

    public World() {
        map = new ArrayList<>();
        stk = new ArrayList<>();
    }

    public ArrayList<Integer> getMap() {
        return map;
    }

    public ArrayList<Integer> genMap(String fileName) {
        map = MapUtil.genMap(fileName);
        stk = new ArrayList<>();
        return map;
    }

    public ArrayList<Integer> addBlock(int num) {
        if(stk.isEmpty()) {
            stk.add(num);
            return stk;
        } else {

            int f = stk.get(0), s = num;
            if(f == s) {
                stk.remove(0);
                return stk;
            }

            if(BlockUtil.check(map, f, s)) {
                map.set(f, 0);
                map.set(s, 0);

                stk.add(num);
            } else {
                stk.set(0, num);
            }

            return stk;
        }
    }

    public void clearStk() {
        stk.remove(1);
        stk.remove(0);
    }
}

package com.dreamtea.Game.Utils;

import java.util.ArrayList;

public class BlockUtil {

    public static boolean isEmpty(ArrayList<Integer> map, int num) {
        return map.get(num).equals(0);
    }
    public static boolean isEmpty(ArrayList<Integer> map, int x, int y) {
        return isEmpty(map, x * 10 + y);
    }


    public static boolean checkStraight(ArrayList<Integer> map, int x1, int y1, int x2, int y2) {
        boolean result = false;
        int minx = Math.min(x1, x2), maxx = Math.max(x1, x2);
        int miny = Math.min(y1, y2),maxy = Math.max(y1, y2);
        //one step to win
        if(x1 == x2) {
            boolean isBlocking = false;
            for (int i = miny + 1;i <= maxy - 1;++i) {
                if(!isEmpty(map, x1, i)) {
                    isBlocking = true;
                    break;
                }
            }
            if(!isBlocking) {
                result = true;
            }
        }

        if(y1 == y2) {
            boolean isBlocking = false;
            for (int i = minx + 1;i <= maxx - 1;++i) {
                if(!isEmpty(map, i, y1)) {
                    isBlocking = true;
                    break;
                }
            }
            if(!isBlocking) {
                result = true;
            }
        }
        return result;
    }

    public static boolean checkOnce(ArrayList<Integer> map, int x1, int y1, int x2, int y2) {
        if(x1 == x2 || y1 == y2) {
            return false;
        }

        int minx = Math.min(x1, x2), maxx = Math.max(x1, x2);
        int miny = Math.min(y1, y2),maxy = Math.max(y1, y2);

        //check (x1,y2)
        if(isEmpty(map, x1, y2)) {
            if(checkStraight(map, x1, y1, x1, y2) && checkStraight(map, x2, y2, x1, y2)) {
                return true;
            }
        }
        //check (x2,y1)
        if(isEmpty(map, x2, y1)) {
            return checkStraight(map, x1, y1, x2, y1) && checkStraight(map, x2, y2, x2, y1);
        }

        return false;
    }

    public static boolean checkTwo(ArrayList<Integer> map, int x1, int y1, int x2, int y2) {
        for (int i = x1 - 1;i >= 0;--i) {
            if(isEmpty(map, i, y1)) {
                if(isEmpty(map, i, y2) && checkStraight(map, i, y1, i, y2) && checkStraight(map, i, y2, x2, y2)) {
                    return true;
                }
            } else {
                break;
            }
        }

        for (int i = x1 + 1;i < 10;++i) {
            if(isEmpty(map, i, y1)) {
                if(isEmpty(map, i, y2) && checkStraight(map, i, y1, i, y2) && checkStraight(map, i, y2, x2, y2)) {
                    return true;
                }
            } else {
                break;
            }
        }

        for (int i = y1 - 1;i >= 0;--i) {
            if(isEmpty(map, x1, i)) {
                if(isEmpty(map, x2, i) && checkStraight(map, x1, i, x2, i) && checkStraight(map, x2, i, x2, y2)) {
                    return true;
                }
            } else {
                break;
            }
        }

        for (int i = y1 + 1;i < 10;++i) {
            if(isEmpty(map, x1, i)) {
                if(isEmpty(map, x2, i) && checkStraight(map, x1, i, x2, i) && checkStraight(map, x2, i, x2, y2)) {
                    return true;
                }
            } else {
                break;
            }
        }

        return false;
    }

    public static boolean check(ArrayList<Integer> map, int b1, int b2) {
        if(!map.get(b1).equals(map.get(b2))) {
            return false;
        }

        int x1 = b1 / 10, y1 = b1 % 10;
        int x2 = b2 / 10, y2 = b2 % 10;

        if(checkStraight(map, x1, y1, x2, y2)) {
            return true;
        }

        if(checkOnce(map, x1, y1, x2, y2)) {
            return true;
        }

        return checkTwo(map, x1, y1, x2, y2);
    }
}

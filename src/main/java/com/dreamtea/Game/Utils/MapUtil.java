package com.dreamtea.Game.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MapUtil {

    private final static String workingDirectory = "static/map/";

    private static void printMap(ArrayList<Integer> map) {
        int all = 0;
        for (int i = 0,j = 0;i < map.size();++i) {
            if(map.get(i) != 0) {
                all++;
            }
            if(map.get(i) != 10) {
                System.out.print(map.get(i));
            } else {
                System.out.print('X');
            }
            if(++j == 10) {
                System.out.print("\n");
                j = 0;
            }
        }
//        System.out.println("the total is:" + all);
    }

    public static ArrayList<Integer> readMap(String fileName) {
        String realPath = Objects.requireNonNull(MapUtil.class.getClassLoader().getResource(workingDirectory + fileName)).getPath();
        File file = new File(realPath);
        ArrayList<Integer> defaultMap = new ArrayList<>();

        try (Reader reader = new InputStreamReader(new FileInputStream(file))) {
            int tempChar;
            while ((tempChar = reader.read()) != -1) {
                char curIn = (char) tempChar;
                if(curIn == '\r' || curIn == '\n') {
                    continue;
                }
                int v;
                if(curIn == '*') {
                    v = 1;
                } else {
                    v = 0;
                }
                defaultMap.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        printMap(defaultMap);

        return defaultMap;
    }

    public static ArrayList<Integer> genMap(ArrayList<Integer> defaultMap) {
        int all = 0;
        for(Integer i : defaultMap) {
            if(i.equals(1)) {
                all ++;
            }
        }

        if(all % 2 != 0 || all < 20) {
            throw new IllegalArgumentException("counts of map don't div by 2");
        }

        all /= 2;

//        System.out.println("pairs = " + all);

        ArrayList<Integer> waitToUse = new ArrayList<>();
        int loop = all / 10, remain = all % 10;
        for (int i = 0;i < loop;++i) {
            for (int j = 1;j <= 10;++j) {
                waitToUse.add(j);
                waitToUse.add(j);
            }
        }
        for(int i = 1;i <= remain;++i) {
            waitToUse.add(i);
            waitToUse.add(i);
        }

//        System.out.println(waitToUse.size());

        //TODO: gen random
        Random gen = new Random();
        for (int i = waitToUse.size() - 1;i >= 0;--i) {
            int l = i,r = gen.nextInt(waitToUse.size()) % (i + 1);
            int vl = waitToUse.get(l),vr = waitToUse.get(r);
            waitToUse.set(r, vl);
            waitToUse.set(l, vr);
        }

        for (int i = 0,j = 0;i < defaultMap.size();++i) {
            if(defaultMap.get(i) == 0) {
                continue;
            }
            defaultMap.set(i, waitToUse.get(j++));
        }

//        printMap(defaultMap);

        return defaultMap;
    }

    public static ArrayList<Integer> genMap(String fileName) {
        return genMap(readMap(fileName));
    }

    public static void main(String[] args) {
        ArrayList<Integer> testMap = genMap(readMap("test.txt"));
    }
}

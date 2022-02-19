package com.dreamtea.Domain;

import com.dreamtea.Utils.BlockUtil;

public class Block {
    public int id;
    public int num;
    public int x;
    public int y;
    private static int BlockId = 0;

    public Block(int num, int x, int y) {
        this.id = ++BlockId;
        this.num = num;
        this.x = x;
        this.y = y;
    }
}

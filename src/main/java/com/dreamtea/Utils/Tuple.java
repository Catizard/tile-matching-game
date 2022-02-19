package com.dreamtea.Utils;

public class Tuple<T1, T2, T3> {
    private T1 x;
    private T2 y;
    private T3 z;

    public Tuple(T1 x, T2 y, T3 z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public T1 getX() {
        return x;
    }

    public void setX(T1 x) {
        this.x = x;
    }

    public T2 getY() {
        return y;
    }

    public void setY(T2 y) {
        this.y = y;
    }

    public T3 getZ() {
        return z;
    }

    public void setZ(T3 z) {
        this.z = z;
    }
}

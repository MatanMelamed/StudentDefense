package com.student.defense.Utils;

public class Vector2D {
    public int x, y;

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public float DistanceTo(float ox, float oy) {
        return (float) Math.sqrt(Math.pow(x - ox, 2.0) + Math.pow(y - oy, 2.0));
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }
}

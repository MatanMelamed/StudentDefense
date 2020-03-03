package com.student.defense.Model.Maps;

import com.badlogic.gdx.graphics.Texture;
import com.student.defense.Model.Towers.Tower;

public class Tile {

    private int x, y;
    private Texture texture;
    private Tower tower;
    private boolean isPath;

    Tile(int x, int y, Texture texture, boolean isPath) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.isPath = isPath;
        this.tower = null;
    }


    public boolean hasTower() {
        return tower != null;
    }

    public void BuildTower(Tower t) {
        this.tower = t;
    }

    public void DestroyTower(){
        this.tower = null;
    }

    public Tower GetTower() {return tower;}


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isPath() {
        return isPath;
    }

    public Tower getTower() {
        return tower;
    }
}

package com.student.defense.Model.Towers.TowerEffects;

import com.badlogic.gdx.graphics.Color;
import com.student.defense.Model.Maps.GameMap;

public class CircleTarget extends TowerEffect {

    public CircleTarget(float x, float y) {
        super(1, false);
        texture = AOE.GetCircleTexture(70, Color.RED, false);
        xPos = x + GameMap.HalfTile + texture.getWidth() / 2;
        yPos = y + GameMap.HalfTile + texture.getWidth() / 2;
    }

    @Override
    public void Dispose() {
        texture.dispose();
    }
}

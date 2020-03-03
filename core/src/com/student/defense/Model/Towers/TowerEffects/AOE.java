package com.student.defense.Model.Towers.TowerEffects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.student.defense.Model.Maps.GameMap;

public class AOE extends TowerEffect {

    public AOE(float radius) {
        super(1f);

        texture = GetCircleTexture((int) (radius * GameMap.TileResolution), Color.SALMON, true);
    }

    public static Texture GetCircleTexture(int radius, Color color, boolean isFilled) {
        Pixmap pixmap = new Pixmap(2 * radius + 1, 2 * radius + 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        if (isFilled)
            pixmap.fillCircle(radius, radius, radius);
        else
            pixmap.drawCircle(radius, radius, radius);
        pixmap.drawLine(radius, radius, 2 * radius, radius);
        pixmap.setFilter(Pixmap.Filter.NearestNeighbour);

        Texture texture = new Texture(pixmap);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return texture;
    }

    @Override
    public void SetPosition(float x, float y) {

        super.SetPosition(x + GameMap.HalfTile, y + GameMap.HalfTile);
    }

    @Override
    public void Dispose() {
        texture.dispose();
    }
}

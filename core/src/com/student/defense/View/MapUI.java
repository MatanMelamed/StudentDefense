package com.student.defense.View;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.student.defense.Model.Towers.Tower;
import com.student.defense.Model.Enemies.Enemy;
import com.student.defense.Model.Maps.GameMap;
import com.student.defense.Model.Maps.Tile;
import com.student.defense.Model.Towers.TowerEffects.TowerEffect;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapUI extends LevelUIComponent {

    private List<TowerEffect> beforeObjects;
    private List<TowerEffect> afterObjects;

    private Tile selectedTowerTile;

    class Item {
        int x, y;
        Texture texture;

        Item(int x, int y, Texture texture) {
            this.x = x;
            this.y = y;
            this.texture = texture;
        }
    }

    private Item upgrade;
    private Item sell;

    MapUI(GUIManager guiManager) {
        super(guiManager);
        selectedTowerTile = null;

        upgrade = new Item(0, 0, TexturesManager.GetTexture(Textures.UPGRADE));
        sell = new Item(0, 0, TexturesManager.GetTexture(Textures.SELL));

        beforeObjects = new ArrayList<>();
        afterObjects = new ArrayList<>();
    }

    void AddTowerEffect(TowerEffect effect) {
        if (effect.IsBeforeOtherEffects()) {
            beforeObjects.add(effect);
        } else {
            afterObjects.add(effect);
        }
    }

    void RemoveTowerEffect(TowerEffect effect) {
        if (effect.IsBeforeOtherEffects()) {
            beforeObjects.remove(effect);
        } else {
            afterObjects.remove(effect);
        }
    }

    @Override
    void SetArea(Rectangle newArea) {
        GameMap map = level.GetMap();
        if (map.GetHeight() < newArea.height) {
            newArea.height = map.GetHeight();
        }
        if (map.GetWidth() < newArea.width) {
            newArea.height = map.GetWidth();
        }

        super.SetArea(newArea);
    }

    @Override
    protected void RenderExecution(float dt) {
        batch.begin();

        DrawMap();

        DrawEffectsList(beforeObjects);
        DrawEnemies();
        DrawTowers();
        DrawSelectedTower();
        DrawEffectsList(afterObjects);

        batch.end();
    }

    private void DrawSelectedTower() {
        if (selectedTowerTile != null) {
            manager.DrawTexture(sell.texture, sell.x, sell.y);
            manager.DrawTexture(upgrade.texture, upgrade.x, upgrade.y);
            batch.end();
            Tower tower = selectedTowerTile.GetTower();
            int xPos = tower.GetPosition().x + (int) area.x;
            int yPos = tower.GetPosition().y + (int) area.y;
            manager.DrawCircle(xPos, yPos, (int) (tower.GetRange() * 64), Color.CYAN, ShapeRenderer.ShapeType.Line);
            batch.begin();
        }
    }

    void towerSelected(Tile tile) {
        sell.x = (int) (tile.getX() + area.x - 74);
        sell.y = (int) (tile.getY() + area.y);

        upgrade.x = (int) (tile.getX() + area.x + 74);
        upgrade.y = (int) (tile.getY() + area.y);

        selectedTowerTile = tile;
    }

    void towerDeselect() {
        selectedTowerTile = null;
    }

    boolean isUpgrade(int x, int y) {
        if (selectedTowerTile == null) return false;
        return upgrade.x <= x && x <= upgrade.x + 64 && upgrade.y <= y && y <= upgrade.y + 64;
    }

    boolean isSell(int x, int y) {
        if (selectedTowerTile == null) return false;
        return sell.x <= x && x <= sell.x + 64 && sell.y <= y && y <= sell.y + 64;
    }

    private void DrawMap() {
        for (Tile[] tiles : level.GetMap().GetTiles()) {
            for (Tile tile : tiles) {
                manager.DrawTexture(tile.getTexture(), tile.getX() + area.x, tile.getY() + area.y);
            }
        }
    }

    private void DrawEnemies() {
        for (Enemy enemy : level.GetEnemies()) {
            DrawEnemy(enemy);
        }
    }

    private void DrawEnemy(Enemy enemy) {
        manager.DrawTexture(enemy.texture, enemy.GetXPosition() + area.x, enemy.GetYPosition() + area.y);
        DrawHealthBar(enemy);
        DrawEnemyName(enemy);

//        // draw collider
//        batch.end();
//        shapeRenderer.setColor(Color.GREEN);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        Rectangle rect = enemy.GetCollider();
//        shapeRenderer.rect(rect.x + offset.x, GUIManager.GetManager().InvertY((int) (rect.y + offset.y), (int) rect.height), rect.width, rect.height);
//        shapeRenderer.end();
//        batch.begin();
    }

    private void DrawEnemyName(Enemy enemy) {
        GUIManager.GetManager().DrawText(enemy.GetType().typeName,
                enemy.GetXPosition() + area.x,
                enemy.GetYPosition() + area.y - 50, 0.7f);
    }

    private void DrawHealthBar(Enemy enemy) {
        batch.end();

        // Black Frame
        int barWidth = 74;
        int barHeight = 20;
        int x = (int) (enemy.GetXPosition()) + GameMap.HalfTile - (barWidth / 2) + (int) area.x;
        int y = (int) (enemy.GetYPosition()) - 5 - barHeight + (int) area.y;
        GUIManager.GetManager().DrawRectangle(x, y, barWidth, barHeight, Color.BLACK, ShapeRenderer.ShapeType.Filled);

        // Inner Red Fill
        int frameWidth = 3;
        barWidth -= frameWidth * 2;
        barHeight -= frameWidth * 2;
        x += frameWidth;
        y += frameWidth;
        barWidth *= ((float) enemy.GetHealth() / enemy.GetMaxHealth());
        GUIManager.GetManager().DrawRectangle(x, y, barWidth, barHeight, Color.RED, ShapeRenderer.ShapeType.Filled);

        batch.begin();
    }

    private void DrawTowers() {
        for (Tower t : level.GetTowers()) {
            DrawTower(t);
        }
    }

    private void DrawTower(Tower t) {
        manager.DrawTexture(t.GetTexture(), (t.GetPosition().x - GameMap.HalfTile) + area.x,
                (t.GetPosition().y - GameMap.HalfTile) + area.y);
    }

    private void DrawEffectsList(List<TowerEffect> list) {
        Iterator<TowerEffect> iter = list.iterator();

        while (iter.hasNext()) {
            TowerEffect effect = iter.next();
            if (effect.IsAlive()) {
                manager.DrawTexture(effect.GetTexture(), effect.GetXPosition() + area.x, effect.GetYPosition() + area.y);
            } else {
                effect.Dispose();
                iter.remove();
            }
        }
    }
}

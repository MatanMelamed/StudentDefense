package com.student.defense.Model.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.student.defense.Utils.Vector2D;
import com.student.defense.Model.Enemies.Enemy;
import com.student.defense.Model.Maps.GameMap;
import com.student.defense.Model.Maps.Tile;
import com.student.defense.Model.Towers.TowerEffects.TowerEffect;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public abstract class Tower implements Updatable {
    public static BlockingQueue<Enemy> VisibleEnemiesOnLevel;

    // Broadcast
    PropertyChangeSupport support;
    public static String FIRE_EVENT = "FIRE_EVENT";

    // General
    Vector2D position;
    Texture texture;
    boolean usedBefore;

    // Usage
    float range;        // radius
    float frequency;    // seconds between use
    float cooldown;     // used for shoot management
    int damage;
    TowerType type;
    int level;
    List<Enemy> enemiesInRange;

    Tower(Texture texture) {
        this.texture = texture;
        this.position = new Vector2D();
        support = new PropertyChangeSupport(this);
        enemiesInRange = new ArrayList<>();
        frequency = 0;
        range = 0;
        level = 1;
        usedBefore = false;
    }
    public void AddPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void PlaceOnTile(Tile placement) {
        usedBefore = true;
        position.x = placement.getX() + GameMap.TileResolution / 2;
        position.y = placement.getY() + GameMap.TileResolution / 2;
    }

    public boolean IsUsedBefore() {return usedBefore;}

    public TowerType GetType() {
        return type;
    }

    @Override
    public void Update(float dt) {
        UpdateEnemiesInRange();

        if (cooldown <= 0) {
            if (!enemiesInRange.isEmpty()) {
                DoTowerSkill();
                cooldown = frequency;
                support.firePropertyChange(FIRE_EVENT, null, GetTowerEffect());
            }
        } else {
            cooldown -= dt;
        }
    }

    public void Upgrade() {
        level++;
        OnUpgrade();
    }

    protected abstract void OnUpgrade();

    private void UpdateEnemiesInRange() {
        enemiesInRange.clear();

        for (Enemy e : VisibleEnemiesOnLevel) {
            float distanceInPixels = position.DistanceTo(e.GetXPosition() + GameMap.HalfTile, e.GetYPosition() + GameMap.HalfTile);
            if (distanceInPixels / GameMap.TileResolution <= range) {
                enemiesInRange.add(e);
            }
        }
    }

    abstract void DoTowerSkill();

    abstract TowerEffect GetTowerEffect();

    public Texture GetTexture() {
        return texture;
    }

    public Vector2D GetPosition() {
        return position;
    }

    public float GetRange() {
        return range;
    }

    public int GetLevel() {
        return level;
    }
}

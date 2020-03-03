package com.student.defense.Model.Towers.TowerEffects;

import com.badlogic.gdx.graphics.Texture;
import com.student.defense.Model.Towers.Updatable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TowerEffect implements Updatable {

    public static String EFFECT_ENDED = "EFFECT_ENDED";
    PropertyChangeSupport support;

    private float ttl;
    float xPos, yPos;

    private boolean isBefore;
    Texture texture;
    int damage;

    public TowerEffect(float ttl) {
        this(ttl, true);
    }
    public void AddPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
    public TowerEffect(float ttl, boolean isBefore) {
        this.ttl = ttl;
        this.isBefore = isBefore;
        support = new PropertyChangeSupport(this);
    }

    public void SetPosition(float x, float y) {
        xPos = x;
        yPos = y;
    }

    public boolean IsBeforeOtherEffects() {return isBefore;}

    public void Update(float dt) {
        ttl -= dt;
    }

    public boolean IsAlive() {return ttl > 0f;}

    public float GetXPosition() {
        return xPos - texture.getWidth();
    }

    public float GetYPosition() {
        return yPos - texture.getHeight();
    }

    public Texture GetTexture() {
        return texture;
    }

    public void Dispose() {}

    public void SetTexture(Texture texture) {
        this.texture = texture;
    }

    public void SetDamage(int damage) {
        this.damage = damage;
    }
}

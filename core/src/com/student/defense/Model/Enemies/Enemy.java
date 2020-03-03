package com.student.defense.Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.student.defense.Model.Towers.Updatable;
import com.student.defense.Model.Maps.GameMap;
import com.student.defense.Model.Maps.Waypoint;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public abstract class Enemy implements Updatable {

    public static String ENEMY_DIED = "ENEMY_DIED";
    public static String ENEMY_REACHED_TARGET = "ENEMY_REACHED_TARGET";

    private float xPos, yPos;
    private Rectangle collider;
    public Texture texture;

    private float movementSpeed;
    private int maxHealth;
    private int health;

    private Waypoint target;
    EnemyType type;

    // Broadcast
    private PropertyChangeSupport support;


    public Enemy(Texture t, float speed, int health, Waypoint startingWaypoint) {
        this.texture = t;
        this.movementSpeed = speed;
        this.health = maxHealth = health;
        this.target = startingWaypoint;
        this.xPos = target.GetLocation().x;
        this.yPos = target.GetLocation().y;
        this.collider = new Rectangle(0, 0, t.getWidth() / (float) Math.sqrt(2), t.getHeight() / (float) Math.sqrt(2));
        CalculateBound();
        support = new PropertyChangeSupport(this);
    }

    public void AddPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    private void CalculateBound() {
        collider.x = xPos + GameMap.HalfTile - (collider.width / 2);
        collider.y = yPos + GameMap.HalfTile - (collider.height / 2);
    }

    public void Update(float dt) {
        if (target == null) { return; }

        float xDirection = target.GetLocation().x - xPos > 0 ? 1 : (target.GetLocation().x - xPos) < 0 ? -1 : 0;
        xPos += dt * movementSpeed * xDirection;

        float yDirection = target.GetLocation().y - yPos > 0 ? 1 : (target.GetLocation().y - yPos) < 0 ? -1 : 0;
        yPos += dt * movementSpeed * yDirection;


        if (IsAtWaypoint()) {
            xPos = target.GetLocation().x;
            yPos = target.GetLocation().y;
            target = target.GetNext();

            if (target == null) {
                support.firePropertyChange(ENEMY_REACHED_TARGET, null, this);
            }
        }

        CalculateBound();
    }

    public EnemyType GetType() {
        return type;
    }

    public Rectangle GetCollider() {
        return collider;
    }

    private boolean IsAtWaypoint() {
        float margin = 1f;
        return (Math.abs(target.GetLocation().x - xPos) < margin) && (Math.abs(target.GetLocation().y - yPos) < margin);
    }

    public void TakeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            support.firePropertyChange(ENEMY_DIED, null, this);
        }
    }

    public int GetHealth() {
        return health;
    }

    public int GetMaxHealth() {
        return maxHealth;
    }

    public float GetXPosition() {
        return xPos;
    }

    public float GetYPosition() {
        return yPos;
    }
}
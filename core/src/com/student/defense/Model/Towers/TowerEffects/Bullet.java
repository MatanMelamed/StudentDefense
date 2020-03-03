package com.student.defense.Model.Towers.TowerEffects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.student.defense.Model.Maps.GameMap;
import com.student.defense.Model.Towers.Tower;
import com.student.defense.Model.Enemies.Enemy;
import com.student.defense.Utils.Vector2D;


public class Bullet extends TowerEffect {

    private double angle;
    private float speed;
    private Rectangle bound;


    public Bullet(Vector2D source, Enemy target, int damage, Texture texture) {
        super(3, false);
        // basic interaction
        this.texture = texture;
        this.damage = damage;
        this.speed = 600;
        this.bound = new Rectangle(0, 0, texture.getWidth() / (float) Math.sqrt(2), texture.getHeight() / (float) Math.sqrt(2));
        CalculateBound();

        // location and direction
        angle = Math.atan2(target.GetYPosition() - source.y, target.GetXPosition() - source.x);
        xPos = source.x + GameMap.HalfTile;
        yPos = source.y + GameMap.HalfTile;
    }

    void CalculateBound() {
        bound.x = xPos + GameMap.HalfTile - (bound.width / 2);
        bound.y = yPos + GameMap.HalfTile - (bound.height / 2);
    }

    @Override
    public void SetTexture(Texture texture) {
        super.SetTexture(texture);
        bound.width = texture.getWidth() / (float) Math.sqrt(2);
        bound.height = texture.getHeight() / (float) Math.sqrt(2);
        CalculateBound();
    }

    @Override
    public void Update(float dt) {
        super.Update(dt);
        Move(dt);
    }

    private void Move(float dt) {
        xPos = xPos + ((float) Math.cos(angle) * speed * dt);
        yPos = yPos + ((float) Math.sin(angle) * speed * dt);
        bound.x = xPos;
        bound.y = yPos;

        Enemy enemy;
        if ((enemy = Hit()) != null) {
            enemy.TakeDamage(damage);
            support.firePropertyChange(EFFECT_ENDED, null, this);
        }
    }

    private Enemy Hit() {
        for (Enemy enemy : Tower.VisibleEnemiesOnLevel) {
            if (Intersector.overlaps(enemy.GetCollider(), bound)) {
                return enemy;
            }
        }
        return null;
    }
}

package com.student.defense.Model.Towers;

import com.student.defense.Model.Enemies.Enemy;
import com.student.defense.View.Textures;
import com.student.defense.View.TexturesManager;
import com.student.defense.Model.Towers.TowerEffects.Bullet;
import com.student.defense.Model.Towers.TowerEffects.TowerEffect;


public class VideoTower extends Tower {

    private Enemy target;

    VideoTower() {
        super(TexturesManager.GetTexture(Textures.TOWER_VT1));
        frequency = 1;
        range = 2f;
        damage = 35;
        type = TowerType.VIDEO;
    }

    @Override
    protected void OnUpgrade() {
        texture = TexturesManager.GetTexture(Textures.TOWER_VT2);
        frequency = 1.5f;
        range = 2.5f;
        damage = 55;
    }

    @Override
    void DoTowerSkill() {
        target = enemiesInRange.iterator().next();
        //support.firePropertyChange(Tower.FIRE_EVENT, null, new CircleTarget(target.GetXPosition(), target.GetYPosition()));
    }

    @Override
    TowerEffect GetTowerEffect() {
        return new Bullet(position, target, damage, TexturesManager.GetTexture(Textures.PROJECTILE1));
    }
}

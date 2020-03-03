package com.student.defense.Model.Towers;

import com.student.defense.Model.Enemies.Enemy;
import com.student.defense.Model.Towers.TowerEffects.Bullet;
import com.student.defense.Model.Towers.TowerEffects.TowerEffect;
import com.student.defense.View.Textures;
import com.student.defense.View.TexturesManager;

public class TutorTower extends Tower {


    private Enemy target;

    TutorTower() {
        super(TexturesManager.GetTexture(Textures.TOWER_TUTOR1));
        frequency = 1.5f;
        range = 2f;
        damage = 45;
        type = TowerType.TUTOR;
    }

    @Override
    protected void OnUpgrade() {
        texture = TexturesManager.GetTexture(Textures.TOWER_TUTOR2);
        frequency = 2f;
        range = 2.5f;
        damage = 65;
    }

    @Override
    void DoTowerSkill() {
        target = enemiesInRange.iterator().next();
    }

    @Override
    TowerEffect GetTowerEffect() {
        return new Bullet(position, target, damage, TexturesManager.GetTexture(Textures.PROJECTILE1));
    }
}

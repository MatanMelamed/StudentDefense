package com.student.defense.Model.Towers;

import com.student.defense.Model.Enemies.Enemy;
import com.student.defense.Model.Maps.GameMap;
import com.student.defense.View.Textures;
import com.student.defense.View.TexturesManager;
import com.student.defense.Model.Towers.TowerEffects.AOE;
import com.student.defense.Model.Towers.TowerEffects.TowerEffect;


class RitalinTower extends Tower {

    RitalinTower() {
        super(TexturesManager.GetTexture(Textures.TOWER_RITALIN1));
        frequency = 2;
        range = 1.5f;
        damage = 35;
        type = TowerType.RITALIN;
    }

    @Override
    protected void OnUpgrade() {
        texture = TexturesManager.GetTexture(Textures.TOWER_RITALIN2);
        frequency = 2.5f;
        range = 2;
        damage = 45;
    }

    @Override
    void DoTowerSkill() {
        for (Enemy enemy : enemiesInRange) {
            enemy.TakeDamage(damage);
        }
    }



    @Override
    TowerEffect GetTowerEffect() {
        TowerEffect effect = new AOE(1f);
        effect.SetPosition(position.x+ GameMap.HalfTile, position.y+ GameMap.HalfTile);
        return effect;
    }
}

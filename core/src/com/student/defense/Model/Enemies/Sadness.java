package com.student.defense.Model.Enemies;

import com.student.defense.Model.Maps.Waypoint;
import com.student.defense.View.Textures;
import com.student.defense.View.TexturesManager;

class Sadness extends Enemy {
    Sadness(Waypoint startingWaypoint) {
        super(TexturesManager.GetTexture(Textures.ENEM_SADNESS), 110, 80, startingWaypoint);
        type = EnemyType.SADNESS;
    }
}

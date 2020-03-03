package com.student.defense.Model.Enemies;

import com.student.defense.Model.Maps.Waypoint;
import com.student.defense.View.Textures;
import com.student.defense.View.TexturesManager;

class Paperwork extends Enemy {
    Paperwork(Waypoint startingWaypoint) {
        super(TexturesManager.GetTexture(Textures.ENEM_PAPERWORK), 80, 100, startingWaypoint);
        type = EnemyType.PAPERWORK;
    }
}

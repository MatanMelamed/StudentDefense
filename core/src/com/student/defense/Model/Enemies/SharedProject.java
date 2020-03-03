package com.student.defense.Model.Enemies;

import com.student.defense.Model.Maps.Waypoint;
import com.student.defense.View.Textures;
import com.student.defense.View.TexturesManager;

class SharedProject extends Enemy {
    SharedProject(Waypoint startingWaypoint) {
        super(TexturesManager.GetTexture(Textures.ENEM_SHARED_PROJ), 125, 90, startingWaypoint);
        type = EnemyType.SHARED_PROJECT;
    }
}

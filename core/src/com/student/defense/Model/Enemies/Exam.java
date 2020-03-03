package com.student.defense.Model.Enemies;

import com.student.defense.Model.Maps.Waypoint;
import com.student.defense.View.Textures;
import com.student.defense.View.TexturesManager;

class Exam extends Enemy {

    Exam(Waypoint startingPoint) {
        super(TexturesManager.GetTexture(Textures.ENEM_EXAM), 60, 250, startingPoint);
        type = EnemyType.EXAM;
    }
}

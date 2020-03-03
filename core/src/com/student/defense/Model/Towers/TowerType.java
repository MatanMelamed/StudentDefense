package com.student.defense.Model.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.student.defense.View.Textures;
import com.student.defense.View.TexturesManager;

public enum TowerType {

    VIDEO("Recorded Lectures", Textures.TOWER_VT1),
    TUTOR("Private Tutor", Textures.TOWER_TUTOR1),
    RITALIN("Ritalin", Textures.TOWER_RITALIN1);

    public final String typeName;
    private Textures textureType;

    TowerType(String typeName, Textures texture) {
        this.typeName = typeName;
        this.textureType = texture;
    }

    public Texture GetBaseTexture() {
        return TexturesManager.GetTexture(textureType);
    }
}

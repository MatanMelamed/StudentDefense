package com.student.defense.Utils;

import com.student.defense.Model.Towers.TowerType;

public class TowerPrescription {

    public TowerType type;
    public int level;

    public TowerPrescription(TowerType type, int level) {
        this.type = type;
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TowerPrescription other = (TowerPrescription) o;

        return type == other.type && level == other.level;
    }

    @Override
    public int hashCode() {
        return type.hashCode() * 31 + Integer.hashCode(level);
    }

    @Override
    public String toString() {
        return "[" + type + ", " + level + "]";
    }
}


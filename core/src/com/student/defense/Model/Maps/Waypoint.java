package com.student.defense.Model.Maps;

import com.student.defense.Utils.Vector2D;

public class Waypoint {
    private Vector2D location;
    private Waypoint next;

    public Waypoint(int x, int y, int resolution) {
        this.location = new Vector2D(x * resolution, y * resolution);
        this.next = null;
    }

    public void SetNext(Waypoint next) {
        this.next = next;
    }

    public Vector2D GetLocation() {
        return location;
    }

    public Waypoint GetNext() {
        return next;
    }
}

package com.student.defense.View;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.student.defense.Model.Levels.Level;


abstract class LevelUIComponent {

    GUIManager manager;
    SpriteBatch batch;

    Rectangle area;
    Level level;

    LevelUIComponent(GUIManager guiManager) {
        this(guiManager, new Rectangle());
    }

    LevelUIComponent(GUIManager guiManager, Rectangle area) {
        this.manager = guiManager;
        this.batch = guiManager.GetBatch();
        this.area = new Rectangle(area.x, area.y, 0, 0);
        this.level = null;
    }

    void SetArea(Rectangle newArea) {
        area = newArea;
    }

    void SetLevel(Level l) {
        level = l;
    }

    final void Render(float dt) {
        if (level != null) {
            RenderExecution(dt);
        }
    }

    public Rectangle GetArea() {
        return area;
    }

    protected abstract void RenderExecution(float dt);

    public void Dispose() {}
}

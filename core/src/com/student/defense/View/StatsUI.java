package com.student.defense.View;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class StatsUI extends LevelUIComponent {

    private String[] prefixes;
    private String[] fullStrings;

    StatsUI(GUIManager guiManager) {
        this(guiManager, new Rectangle());
    }

    StatsUI(GUIManager guiManager, Rectangle area) {
        super(guiManager, area);
        prefixes = new String[]{"Level: ", "Lives: ", "Money: ", "Score: "};
        fullStrings = new String[prefixes.length];
    }

    @Override
    protected void RenderExecution(float dt) {
        fullStrings[0] = prefixes[0] + level.GetLevelName();
        fullStrings[1] = prefixes[1] + level.GetLives();
        fullStrings[2] = prefixes[2] + level.GetMoney();
        fullStrings[3] = prefixes[3] + level.GetScore();

        RenderFullStrings();
    }

    private void RenderFullStrings() {
        int heightSeparation = 40;

        manager.DrawRectangle(area, Color.DARK_GRAY, ShapeRenderer.ShapeType.Filled);

        batch.begin();
        for (int i = 0; i < fullStrings.length; i++) {
            manager.DrawText(fullStrings[i], area.x + 20, area.y + heightSeparation * i, 1.5f);
        }
        batch.end();
    }
}

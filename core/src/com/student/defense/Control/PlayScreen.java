package com.student.defense.Control;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.student.defense.Model.Levels.Level;
import com.student.defense.View.GUIManager;
import com.student.defense.View.TexturesManager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PlayScreen implements Screen {

    private GUIManager levelGuiManager;
    Level currentLevel;

    private boolean pause;

    private static PlayScreen screen = new PlayScreen();

    public static PlayScreen GetPlayScreen() { return PlayScreen.screen; }

    private PlayScreen() {}


    public void TogglePause() {
        pause = !pause;
    }

    public void Start(Level l, Game main) {
        currentLevel = l;
        l.AddPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(Level.LevelCompleted)) {
                    System.out.println("Yay");
                } else if (evt.getPropertyName().equals(Level.LevelFailed)) {
                    System.out.println("BLYAT");
                }
            }
        });
        levelGuiManager = GUIManager.GetManager();
        levelGuiManager.SetLevelToRender(l);

        currentLevel.StartLevel();
        main.setScreen(this);
    }

    @Override
    public void render(float delta) {
        if (!pause) {
            currentLevel.Update(delta);
        }
        levelGuiManager.renderLevel(delta);
    }

    @Override
    public void resize(int width, int height) {
        levelGuiManager.Resize(width, height);
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        levelGuiManager.Dispose();
        TexturesManager.Dispose();
    }
}

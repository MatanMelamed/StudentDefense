package com.student.defense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.student.defense.Control.MouseInputHandler;
import com.student.defense.Model.Levels.FirstSemester;
import com.student.defense.Control.PlayScreen;
import com.student.defense.Model.Towers.TowerFactory;
import com.student.defense.View.TexturesManager;

public class StudentDefense extends Game {
    private MouseInputHandler mouseInputHandler;
    private PlayScreen playScreen;


    @Override
    public void create() {
        init();
    }

    private void init() {
        TexturesManager.InitializeTexturesManager();
        TowerFactory.Initialize();
        mouseInputHandler = new MouseInputHandler();
        Gdx.input.setInputProcessor(mouseInputHandler);

        playScreen = PlayScreen.GetPlayScreen();
        playScreen.Start(new FirstSemester(), this);
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void render() { super.render(); }

    @Override
    public void dispose() { }
}

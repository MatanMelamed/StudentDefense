package com.student.defense.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.student.defense.StudentDefense;

public class DesktopLauncher {

    public static void Run() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.fullscreen = false;
        config.width = 1366;
        config.height = 768;
        config.foregroundFPS = 144;
        new LwjglApplication(new StudentDefense(), config);
    }


    public static void main(String[] arg) {
        Run();
    }
}

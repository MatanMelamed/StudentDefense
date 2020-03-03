package com.student.defense.Utils;

import java.util.ArrayList;
import java.util.List;

public class ActionTimer {

    private float cooldown;
    private boolean isOn;
    private List<Runnable> actions;

    public ActionTimer() {
        cooldown = 0;
        isOn = false;
        actions = new ArrayList<>();
    }

    public void AddActionToTimeUp(Runnable r) {
        actions.add(r);
    }

    public void StartTimer() {
        StartTimer(3);
    }

    private void StartTimer(float seconds) {
        if (actions.isEmpty()) { return;}
        isOn = true;
        cooldown = seconds;
    }

    public void Update(float dt) {
        if (!isOn) { return;}

        cooldown -= dt;
        if (cooldown <= 0) {
            TimeUp();
        }
    }

    public float GetTimeLeft() {return cooldown;}

    private void TimeUp() {
        isOn = false;
        for (Runnable r : actions) {
            r.run();
        }
    }
}

package com.student.defense.Model.Levels;

import com.student.defense.Model.Enemies.Enemy;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;


public class Spawner {

    public static String SpawnEvent = "Spawn";
    public static String WaveEndedEvent = "WaveCompleted";
    public static String SpawnerCompletedEvent = "SpawnerFinished";

    // Data
    private List<Queue<Enemy>> waves;
    private int currentWave;

    // Timing
    private Timer timer;
    private volatile boolean shouldStopSpawn;

    // Broadcast
    private PropertyChangeSupport support;

    Spawner() {
        support = new PropertyChangeSupport(this);
        currentWave = 0;
    }

    public void AddPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    void InitWaves(List<Queue<Enemy>> waves) {
        this.waves = waves;
    }

    public final void StartWave() {
        shouldStopSpawn = false;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!shouldStopSpawn) {
                    Spawn();
                } else {
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    private void Spawn() {
        if (currentWave >= waves.size()) {
            shouldStopSpawn = true;
            support.firePropertyChange(Spawner.SpawnerCompletedEvent, null, null);
        } else if (waves.get(currentWave).isEmpty()) {
            shouldStopSpawn = true;
            currentWave++;
            support.firePropertyChange(Spawner.WaveEndedEvent, null, null);

        } else {
            support.firePropertyChange(Spawner.SpawnEvent, null, waves.get(currentWave).remove());
        }
    }
}

package com.student.defense.Model.Levels;

import com.student.defense.Model.Enemies.Enemy;
import com.student.defense.Model.Enemies.EnemyFactory;
import com.student.defense.Model.Enemies.EnemyType;
import com.student.defense.Model.Maps.GameMap;
import com.student.defense.Model.Maps.Waypoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class FirstSemester extends Level {


    public FirstSemester() {
        super();
        money = 9999999;
        // init map
        map = new GameMap("First Semester", "maps/level01.csv");

        // init spawner
        spawner.InitWaves(InitializeWaves(map.GetWaypoints()));
    }

    private List<Queue<Enemy>> InitializeWaves(Waypoint startPoint) {
        EnemyFactory.GetFactory().Initialize(startPoint);
        List<Queue<Enemy>> waves = new ArrayList<>();
        waves.add(GetWave(3, 0, 0, 0));
        waves.add(GetWave(2, 2, 0, 0));
        waves.add(GetWave(1, 1, 2, 0));
        waves.add(GetWave(1, 1, 1, 1));
        return waves;
    }

    private Queue<Enemy> GetWave(int paper, int sad, int shared, int exam) {
        Queue<Enemy> wave_i = new LinkedList<>();

        AddNumEnemyTypeToWave(wave_i, EnemyType.PAPERWORK, paper);
        AddNumEnemyTypeToWave(wave_i, EnemyType.SADNESS, sad);
        AddNumEnemyTypeToWave(wave_i, EnemyType.SHARED_PROJECT, shared);
        AddNumEnemyTypeToWave(wave_i, EnemyType.EXAM, exam);

        return wave_i;
    }

    private void AddNumEnemyTypeToWave(Queue<Enemy> wave, EnemyType type, int amount) {
        for (int i = 0; i < amount; i++) {
            wave.add(EnemyFactory.GetFactory().GetNewEnemy(type));
        }
    }
}

package com.student.defense.Model.Enemies;

import com.student.defense.Model.Maps.Waypoint;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class EnemyFactory {

    private Map<EnemyType, Queue<Enemy>> enemyQueues;
    private static EnemyFactory factory = new EnemyFactory();

    public static EnemyFactory GetFactory() {return factory;}

    private EnemyFactory() {
        enemyQueues = new HashMap<>();
    }

    public void Initialize(Waypoint startingWaypoint) {
        for (EnemyType type : EnemyType.values()) {
            Queue<Enemy> queue = new LinkedList<>();
            for (int i = 0; i < 10; i++) {
                if (type == EnemyType.PAPERWORK) {
                    queue.add(new Paperwork(startingWaypoint));
                } else if (type == EnemyType.SADNESS) {
                    queue.add(new Sadness(startingWaypoint));
                } else if (type == EnemyType.SHARED_PROJECT) {
                    queue.add(new SharedProject(startingWaypoint));
                } else if (type == EnemyType.EXAM) {
                    queue.add(new Exam(startingWaypoint));
                }
            }
            enemyQueues.put(type, queue);
        }
    }

    public Enemy GetNewEnemy(EnemyType type) {
        return enemyQueues.get(type).remove();
    }

    public void Checkout(Enemy enemy) {
        enemyQueues.get(enemy.type).add(enemy);
    }
}

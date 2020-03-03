package com.student.defense.Model.Towers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class TowerFactory {

    private static Map<TowerType, Queue<Tower>> towersQueues;

    public static void Initialize() {
        towersQueues = new HashMap<>();
        for (TowerType type : TowerType.values()) {
            Queue<Tower> queue = new LinkedList<>();
            for (int i = 0; i < 10; i++) {
                if (type == TowerType.VIDEO) {
                    queue.add(new VideoTower());
                } else if (type == TowerType.RITALIN) {
                    queue.add(new RitalinTower());
                } else if (type == TowerType.TUTOR) {
                    queue.add(new TutorTower());
                }
            }
            towersQueues.put(type, queue);
        }
    }

    public static Tower GetNewTower(TowerType type) {
        return towersQueues.get(type).remove();
    }

    public static void Checkout(Tower tower) {
        towersQueues.get(tower.type).add(tower);
    }
}

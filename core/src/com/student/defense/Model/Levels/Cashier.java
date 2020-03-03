package com.student.defense.Model.Levels;

import com.student.defense.Model.Enemies.Enemy;
import com.student.defense.Model.Enemies.EnemyType;
import com.student.defense.Model.Towers.TowerType;
import com.student.defense.Utils.TowerPrescription;


import java.util.HashMap;
import java.util.Map;

public class Cashier {

    private static Map<EnemyType, Integer> enemyBounty;
    private static Map<EnemyType, Integer> enemyExperience;
    private static Map<TowerPrescription, Integer> towerCost;


    private static Cashier cashier = new Cashier();

    public static Cashier GetCashier() {return cashier;}

    private static int amp = 1;

    private Cashier() {
        towerCost = new HashMap<>();
        enemyBounty = new HashMap<>();
        enemyExperience = new HashMap<>();
        int exp = 10, bounty = 85;
        for (EnemyType type : EnemyType.values()) {
            enemyBounty.put(type, bounty);
            enemyExperience.put(type, exp);
            exp += 5;
            bounty += 25;
        }

        int cost = 25;
        for (TowerType type : TowerType.values()) {
            for (int i = 1; i < 3; i++) {
                towerCost.put(new TowerPrescription(type, i), cost);
                cost += 10 * (i + 1);
            }
            cost += 100;
        }
    }

    public static void SetAmplification(int newAmp) {
        if (newAmp > 0)
            amp = newAmp;
    }

    public int GetTowerCost(TowerType type, int level) {
        TowerPrescription tp = new TowerPrescription(type, level);
        if (towerCost.containsKey(tp)) {
            return towerCost.get(tp) * amp;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public int GetScoreRewardFromEnemy(EnemyType type) {
        return enemyExperience.get(type) * amp;
    }

    public int GetMoneyRewardFromEnemy(EnemyType type) {
        return enemyBounty.get(type) * amp;
    }
}

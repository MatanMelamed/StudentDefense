package com.student.defense.Model.Levels;

import com.student.defense.Model.Maps.GameMap;
import com.student.defense.Model.Towers.Tower;
import com.student.defense.Model.Towers.TowerEffects.TowerEffect;
import com.student.defense.Model.Towers.Updatable;
import com.student.defense.Model.Enemies.Enemy;
import com.student.defense.Model.Enemies.EnemyFactory;
import com.student.defense.Utils.ActionTimer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class Level {

    public static String LevelCompleted = "Level Completed";
    public static String LevelFailed = "Level Failed";

    private BlockingQueue<Enemy> enemies;
    GameMap map;
    private List<Tower> towers;
    private BlockingQueue<TowerEffect> towerEffects;

    private Cashier cashier;
    private int score;
    int money;
    private int lives;

    Spawner spawner;
    private ActionTimer waveCooldown;

    // Broadcast
    private PropertyChangeSupport support;

    Level() {
        support = new PropertyChangeSupport(this);

        score = money = 0;
        lives = 5;

        enemies = new ArrayBlockingQueue<>(100);
        towers = new ArrayList<>();
        towerEffects = new ArrayBlockingQueue<>(100);
        cashier = Cashier.GetCashier();
        spawner = new Spawner();

        waveCooldown = new ActionTimer();
        waveCooldown.AddActionToTimeUp(() -> spawner.StartWave());

        // let towers know which enemies are on the map
        Tower.VisibleEnemiesOnLevel = enemies;

        // set spawner and new enemies event handlers
        spawner.AddPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(Spawner.SpawnEvent)) {
                    final Enemy enemy = (Enemy) evt.getNewValue();
                    AddEnemy(enemy);
                } else if (evt.getPropertyName().equals(Spawner.WaveEndedEvent)) {
                    waveCooldown.StartTimer();
                } else if (evt.getPropertyName().equals(Spawner.SpawnerCompletedEvent)) {
                    support.firePropertyChange(LevelCompleted, null, null);
                }
            }
        });
    }

    public void AddPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void StartLevel() {
        spawner.StartWave();
    }

    private void AddEnemy(Enemy enemy) {
        enemies.add(enemy);
        enemy.AddPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(Enemy.ENEMY_DIED)) {
                    RemoveEnemy(enemy);
                } else if (evt.getPropertyName().equals(Enemy.ENEMY_REACHED_TARGET)) {
                    lives -= 1;
                    if (lives <= 0) {
                        support.firePropertyChange(LevelFailed, null, null);
                    }
                }
            }
        });
    }

    private void RemoveEnemy(Enemy enemy) {
        enemies.remove(enemy);
        EnemyFactory.GetFactory().Checkout(enemy);
        money += cashier.GetMoneyRewardFromEnemy(enemy.GetType());
        score += cashier.GetScoreRewardFromEnemy(enemy.GetType());
    }

    public void ChargeMoney(int moneyToCharge) {
        money -= moneyToCharge;
    }

    public void EarnMoney(int moneyToEarn) {
        money += moneyToEarn;
    }

    public void Update(float dt) {
        waveCooldown.Update(dt);
        UpdateList(enemies, dt);
        UpdateList(towers, dt);
        UpdateList(towerEffects, dt);
    }

    private void UpdateList(Collection<? extends Updatable> collection, float dt) {
        Iterator<? extends Updatable> iter = collection.iterator();
        while (iter.hasNext()) {
            iter.next().Update(dt);
        }
    }

    // Getters Setters
    public void AddTower(Tower tower) {
        towers.add(tower);
    }

    public void RemoveTower(Tower tower) {
        towers.remove(tower);
    }

    public void AddEffect(TowerEffect effect) {
        towerEffects.add(effect);
    }

    public void RemoveEffect(TowerEffect effect) {
        towerEffects.remove(effect);
    }

    public BlockingQueue<Enemy> GetEnemies() {
        return enemies;
    }

    public List<Tower> GetTowers() {
        return towers;
    }

    public GameMap GetMap() {
        return map;
    }

    public int GetScore() {
        return score;
    }

    public int GetMoney() {
        return money;
    }

    public int GetLives() {
        return lives;
    }

    public String GetLevelName() {return map.GetTitle();}

}

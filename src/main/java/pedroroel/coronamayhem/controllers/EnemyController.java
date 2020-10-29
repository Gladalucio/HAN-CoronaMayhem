package pedroroel.coronamayhem.controllers;

import nl.han.ica.oopg.alarm.Alarm;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.entities.*;

import java.util.ArrayList;
import java.util.List;

public class EnemyController extends AlarmController {
    private final List<Enemy> enemies = new ArrayList<>();
    private final String enemySpawnAlarmName = "enemy";
    private float spawnDelay = 3;
    private int maxEnemies = 6;

    public EnemyController(CoronaMayhem world) {
        super(world);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Starts an alarm (timeout) for spawning an enemy
     */
    public void startAlarm() {
        int baseSpawnDelay = 3;

        spawnDelay = enemies.size() < 4 ? 0.75f : baseSpawnDelay;
        Alarm alarm = new Alarm(enemySpawnAlarmName, spawnDelay);
        alarm.addTarget(this);
        alarm.start();
    }

    /**
     * Checks enemy collision with drops
     * @param collisionCtrl Easy reference to the CollisionController
     */
    public void checkCollisionOccurred(CollisionController collisionCtrl) {
        List<Drop> drops = world.getDropCtrl().getDrops();

        /* If no drops are present, enemy collision with drops doesn't need to be checked */
        if (drops.size() < 1) {
            return;
        }

        for(Enemy enemy: enemies) {
            for (Drop drop: drops) {
                boolean collided = collisionCtrl.hasCollisionOccurred(enemy, drop);
                if (collided) {
                    enemy.handleCollisionWith(drop);
                }
            }
        }
    }

    /**
     * Restarts a previously set alarm unless the enemy limit has been exceeded
     */
    protected void restartAlarm() {
        if (enemies.size() < maxEnemies) {
            startAlarm();
        } else {
            restartAlarmDelayed();
        }
    }

    /**
     * Delays setting a new alarm so maxEnemies can be honored
     * Spawning will continue if the max was reached but enemies were killed
     */
    private void restartAlarmDelayed() {
        Alarm alarm = new Alarm("delayedSpawn", spawnDelay);
        alarm.addTarget(this);
        alarm.start();
    }

    /**
     * Acts when alarm is triggered
     * @param alarmName contains the alarm's name but isn't used here
     */
    @Override
    public void triggerAlarm(String alarmName) {
        if (world.getGameStarted() && alarmName.equals(enemySpawnAlarmName)) {
            Enemy.Color enemyColor = Enemy.Color.Yellow;
            int score = world.getScoreboard().getScore();

            if (score > 10) {
                enemyColor = Enemy.Color.Red;
                maxEnemies = 10;
            } else if (score > 5) {
                enemyColor = Enemy.Color.Orange;
                maxEnemies = 8;
            }

            enemies.add(new Enemy(world, enemyColor));
        }
        restartAlarm();
    }

    public void removeEnemy(Enemy enemy) {
        world.deleteGameObject(enemy);
        enemies.remove(enemy);
    }
}

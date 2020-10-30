package pedroroel.coronamayhem.controllers;

import nl.han.ica.oopg.alarm.Alarm;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.entities.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemyController extends AlarmController {
    private final List<Enemy> enemies = new ArrayList<>();

    public EnemyController(CoronaMayhem world) {
        super(world);
        spawnDelay = 3;
        maxSpawned = 6;
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

        Alarm alarm = new Alarm(spawnAlarmName, spawnDelay);
        alarm.addTarget(this);
        alarm.start();
    }

    /**
     * Checks enemy collision with drops
     * @param collisionCtrl Easy reference to the CollisionController
     */
    public void checkCollisionOccurred(CollisionController collisionCtrl) {
        List<Drop> drops = world.getDropCtrl().getDrops();
        removeDeadEnemies();

        /* If no drops are present, enemy collision with drops doesn't need to be checked */
        if (drops.size() < 1) {
            return;
        }

        /* Because enemies will be reduced in health thus removed from the list, the regular for-loop gives a ConcurrentModificationException */
        for (Enemy enemy : enemies) {
            final float maxDist = 10000;
            final float minDist = 5;
            float closestDist = maxDist;
            boolean collisionHappened = false;
            boolean enemyIsColliding = enemy.getIsColliding();

            for (Drop drop : drops) {
                float dist = collisionCtrl.returnDistance(enemy, drop);
                if (dist < closestDist) {
                    closestDist = dist;
                }

                boolean collided = collisionCtrl.hasCollisionOccurred(enemy, drop);

                if (collided && !enemyIsColliding) {
                    enemy.handleCollisionWith(drop);
                    System.out.println("Collision!");
                    collisionHappened = true;
                    enemy.setIsColliding(true);

                }
            }

            if (!collisionHappened && enemyIsColliding && closestDist > minDist && closestDist != maxDist) {
                System.out.println("Collision reset!");
                enemy.setIsColliding(false);
            }
        }
    }

    /**
     * Delays setting a new alarm so maxEnemies can be honored
     * Spawning will continue if the max was reached but enemies were killed
     */
    protected void delayAlarm() {
        Alarm alarm = new Alarm(delayAlarmName, spawnDelay);
        alarm.addTarget(this);
        alarm.start();
    }

    /**
     * Acts when alarm is triggered
     * @param alarmName contains the alarm's name but isn't used here
     */
    @Override
    public void triggerAlarm(String alarmName) {
        if (!world.getGameStarted()) {
            delayAlarm();
            return;
        }

        if (alarmName.equals(spawnAlarmName)) {
            if (enemies.size() >= maxSpawned) {
                delayAlarm();
                return;
            }

            Enemy.Color enemyColor = Enemy.Color.Yellow;
            int score = world.getScoreboard().getScore();

            if (score > 10) {
                enemyColor = Enemy.Color.Red;
                maxSpawned = 10;
            } else if (score > 5) {
                enemyColor = Enemy.Color.Orange;
                maxSpawned = 8;
            }

            enemies.add(new Enemy(world, enemyColor));
        }

        startAlarm();
    }

    public void removeEnemy(Enemy enemy) {
        if (enemies.contains(enemy)) {
            enemies.remove(enemy);
        }
        if (world.getGameObjectItems().contains(enemy)) {
            world.deleteGameObject(enemy);
        }
    }

    private void removeDeadEnemies() {
        Iterator<Enemy> deadIter = enemies.iterator();
        while (deadIter.hasNext()) {
            Enemy enemy = deadIter.next();
            if (enemy.getLives() < 0) {
                System.out.println("Removed dead enemy!");
                deadIter.remove();
                removeEnemy(enemy);
            }
        }
    }
}

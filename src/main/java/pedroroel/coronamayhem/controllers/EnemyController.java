package pedroroel.coronamayhem.controllers;

import nl.han.ica.oopg.alarm.Alarm;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.entities.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Handles spawning and handling of Enemies. All bulk actions are done here, singular Enemy actions are performed in the Enemy class
 * extends AlarmController
 */
public class EnemyController extends AlarmController {
    private final List<Enemy> enemies = new ArrayList<>();

    public EnemyController(CoronaMayhem world) {
        super(world);
        spawnDelay = 3;
        maxSpawned = 6;
    }

    /**
     * Returns a list of all current enemies
     * @return list of enemies
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Starts an alarm (timeout) for spawning an enemy which gets handles in triggerAlarm()
     */
    @Override
    public void startAlarm() {
        int baseSpawnDelay = 3;
        /* Spawns some enemies quickly at the start of the game as well as later on when less than 4 enemies are active */
        spawnDelay = enemies.size() < 4 ? 0.75f : baseSpawnDelay;

        Alarm alarm = new Alarm(spawnAlarmName, spawnDelay);
        alarm.addTarget(this);
        alarm.start();
    }

    /**
     * Checks enemy collision with enemies
     * @param collisionCtrl Reference to the CollisionController
     */
    public void checkCollisionOccurred(CollisionController collisionCtrl) {
        List<Drop> drops = world.getDropCtrl().getDrops();
        /* Remove and despawn dead enemies on every collision cycle
        * This was done this way to prevent ConcurrentModificationExceptions */
        removeDeadEnemies();

        /* If no drops are present, enemy collision with drops doesn't need to be checked */
        if (drops.size() < 1) {
            return;
        }

        for (Enemy enemy : enemies) {
            /* maxDist, minDist, closestDist and collisionHappened are used to check for valid collision and prevent bouncing */
            final float maxDist = 10000;
            final float minDist = 50;
            float closestDist = maxDist;
            boolean collisionHappened = false;

            boolean enemyIsColliding = enemy.getIsColliding();

            for (Drop drop : drops) {
                float dist = collisionCtrl.returnDistance(enemy, drop);
                if (dist < closestDist) {
                    closestDist = dist;
                }
                boolean collided = collisionCtrl.hasCollisionOccurred(enemy, drop);

                /* Only when colliding is true and the enemy wasn't already colliding, continue */
                if (collided && !enemyIsColliding) {
                    enemy.handleCollisionWith(drop);
                    collisionHappened = true;
                    enemy.setIsColliding(true);
                }
            }

            /* Resets the enemy's "isColliding" to false when the collision with any drops is over */
            if (!collisionHappened && enemyIsColliding && closestDist > minDist && closestDist != maxDist) {
//                System.out.println("Collision reset!");
                enemy.setIsColliding(false);
            }
        }
    }

    @Override
    protected void delayAlarm() {
        Alarm alarm = new Alarm(delayAlarmName, spawnDelay);
        alarm.addTarget(this);
        alarm.start();
    }

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

    /**
     * Removes an enemy from both the List of Enemies and the List of GameObjects in the engine
     * @param enemy
     */
    public void removeEnemy(Enemy enemy) {
        if (enemies.contains(enemy)) {
            enemies.remove(enemy);
        }
        if (world.getGameObjectItems().contains(enemy)) {
            world.deleteGameObject(enemy);
        }
    }

    /**
     * Removes all enemies with health lower than 0
     * removal happens in a safe way by using "Iterator" instead of a more traditional "for" to prevent ConcurrentModificationExceptions
     */
    private void removeDeadEnemies() {
        Iterator<Enemy> deadIter = enemies.iterator();
        while (deadIter.hasNext()) {
            Enemy enemy = deadIter.next();
            if (enemy.getLives() < 0) {
//                System.out.println("Removed dead enemy!");
                deadIter.remove();
                removeEnemy(enemy);
            }
        }
    }

    /**
     * Decreases the amount of lives of all enemies by a certain amount
     * @param amount value to decrease lives by
     */
    public void decreaseLivesBy(int amount) {
        for (int i = 0; i < amount; i++) {
            for (Enemy enemy: enemies) {
                enemy.decreaseLives();
            }
        }
    }
}

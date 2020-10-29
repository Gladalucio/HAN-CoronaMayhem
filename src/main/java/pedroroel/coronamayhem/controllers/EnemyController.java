package pedroroel.coronamayhem.controllers;

import nl.han.ica.oopg.alarm.Alarm;
import nl.han.ica.oopg.alarm.IAlarmListener;
import nl.han.ica.oopg.objects.GameObject;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.entities.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemyController implements IAlarmListener {
    private final CoronaMayhem world;
    private final List<Enemy> enemies = new ArrayList<>();
    private final String enemySpawnAlarmName = "enemy";
    private float spawnDelay = 3;
    private int maxEnemies = 6;
    private boolean isColliding = false;
    private Enemy closestEnemy;

    public EnemyController(CoronaMayhem world) {
        this.world = world;
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
    private void restartAlarm() {
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

//    /**
//     * calculates and picks the current closest enemy to the player and handles possible collision
//     */
//    public void entityCollisionOccurred(GameObject objectA, GameObject objectB)
//    {
//        if (enemies.size() < 1) {
//            return;
//        }
//
//        double closestEnemyDistance = 10000.0;
//
//        for(Enemy ce : enemies)
//        {
//            if(ce.getDistanceFrom(objectA) <= closestEnemyDistance)
//            {
//                closestEnemyDistance = ce.getDistanceFrom(objectA);
//                closestEnemy = ce;
//            }
//        }
//        if(objectA.getDistanceFrom(objectB) <= 0.0)
//        {
//            System.out.println("wtf");
//            for(Enemy ae : getEnemies())
//            {
//                ae.decreaseLives();
//            }
//        }
//        if(isColliding == false && closestEnemy.getDistanceFrom(objectA) == 0.0)
//        {
//            if(objectA.getAngleFrom(closestEnemy) >= 160 && objectA.getAngleFrom(closestEnemy) <= 220)
//            {
//                System.out.println("Healed a patient!");
//                isColliding = true;
//                closestEnemy.decreaseLives();
//                world.getScoreboard().increase();
//            }else {
//                System.out.println("Infected!");
//                isColliding = true;
//                ((Player)objectA).decreaseLives();
//                world.getScoreboard().decrease();
//            }
//        }
//        if(isColliding == true && closestEnemy.getDistanceFrom(objectA) != 0.0){
//            isColliding = false;
//        }
//    }
}

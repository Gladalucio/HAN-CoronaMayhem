package pedroroel.coronamayhem.controllers;

import nl.han.ica.oopg.alarm.Alarm;
import nl.han.ica.oopg.alarm.IAlarmListener;
import nl.han.ica.oopg.objects.GameObject;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.entities.Enemy;
import pedroroel.coronamayhem.entities.Player;
import pedroroel.coronamayhem.objects.LockDown;

import java.util.ArrayList;
import java.util.List;

public class EnemyController implements IAlarmListener {
    private final CoronaMayhem world;
    private final List<Enemy> enemiesList = new ArrayList<>();
    private final String enemySpawnAlarmName = "enemy";
    private float spawnDelay = 3;
    private int maxEnemies = 6;
    private boolean isColliding = false;
    private Enemy closestEnemy;

    public EnemyController(CoronaMayhem world) {
        this.world = world;
    }

    public List<Enemy> getAllEnemies() {
        return enemiesList;
    }

    /**
     * Starts an alarm (timeout) for spawning an enemy
     */
    public void startAlarm() {
        int baseSpawnDelay = 3;

        spawnDelay = enemiesList.size() < 4 ? 0.75f : baseSpawnDelay;
        Alarm alarm = new Alarm(enemySpawnAlarmName, spawnDelay);
        alarm.addTarget(this);
        alarm.start();
    }

    /**
     * calculates and picks the current closest enemy to the player and handles possible collision
     */
    public void entityCollisionOccurred(GameObject objectA, GameObject objectB)
    {
        System.out.println(objectA.getDistanceFrom(objectB));
        if (enemiesList.size() < 1) {
            return;
        }

        double closestEnemyDistance = 10000.0;

        for(Enemy ce : enemiesList)
        {
            if(ce.getDistanceFrom(objectA) <= closestEnemyDistance)
            {
                closestEnemyDistance = ce.getDistanceFrom(objectA);
                closestEnemy = ce;
            }
        }
        if(objectA.getDistanceFrom(objectB) <= 0.0)
        {
            System.out.println("wtf");
            for(Enemy ae : getAllEnemies())
            {
                ae.decreaseLives();
            }
        }
        if(isColliding == false && closestEnemy.getDistanceFrom(objectA) == 0.0)
        {
            if(objectA.getAngleFrom(closestEnemy) >= 160 && objectA.getAngleFrom(closestEnemy) <= 220)
            {
                System.out.println("Healed a patient!");
                isColliding = true;
                closestEnemy.decreaseLives();
                world.getScoreboard().increase();
            }else {
                System.out.println("Infected!");
                isColliding = true;
                ((Player)objectA).decreaseLives();
                world.getScoreboard().decrease();
            }
        }
        if(isColliding == true && closestEnemy.getDistanceFrom(objectA) != 0.0){
            isColliding = false;
        }
    }

    /**
     * Restarts a previously set alarm unless the enemy limit has been exceeded
     */
    private void restartAlarm() {
        if (enemiesList.size() < maxEnemies) {
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

            enemiesList.add(new Enemy(world, enemyColor));
        }
        restartAlarm();
    }

    public void removeEnemy(Enemy enemy) {
        world.deleteGameObject(enemy);
        enemiesList.remove(enemy);
    }
}

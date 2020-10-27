package pedroroel.coronamayhem.controllers;

import nl.han.ica.oopg.alarm.Alarm;
import nl.han.ica.oopg.alarm.IAlarmListener;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.entities.Enemy;
import pedroroel.coronamayhem.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class EnemyController implements IAlarmListener {
    private CoronaMayhem world;
    private List<Enemy> enemiesList = new ArrayList<Enemy>();
    private float spawnDelay = 3;
    private int maxEnemies = 5;
    private boolean isColliding = false;
    private static Enemy closestEnemy;
    private Boolean enemyKilled = false;

    public EnemyController(CoronaMayhem world) {
        this.world = world;
    }

    public void init() {
        enemiesList.add(new Enemy(world, Enemy.Color.Yellow, Enemy.SpawnSide.Left));
        enemiesList.add(new Enemy(world, Enemy.Color.Red, Enemy.SpawnSide.Right));
    }

    /**
     * Starts an alarm (timeout) for spawning an enemy
     */
    public void startAlarm() {
        Alarm alarm = new Alarm("enemy", spawnDelay);
        alarm.addTarget(this);
        alarm.start();
    }

    /**
     * returns true if collision is currently happening between player and enemy
     * @return Boolean
     */
    public boolean getIsColliding()
    {
        return isColliding;
    }

    /**
     * returns the closest enemy to the player
     * @return Enemy
     */
    public Enemy getClosestEnemy()
    {
        return closestEnemy;
    }

    /**
     * returns true when the player collides with an enemy at the right angle to kill it
     * @return boolean
     */
    public boolean getEnemyKilled()
    {
        return enemyKilled;
    }

    /**
     * calculates and picks the current closest enemy to the player and returns a true on collision
     */
    public void entityCollisionOccurred(Player player)
    {
        double closestEnemyDistance = 10000.0;

        for(Enemy ce : enemiesList)
        {
            if(ce.getDistanceFrom(player) <= closestEnemyDistance)
            {
                closestEnemyDistance = ce.getDistanceFrom(player);
                closestEnemy = ce;
            }
        }
        if(isColliding == false && closestEnemy.getDistanceFrom(player) == 0.0)
        {
            if(player.getAngleFrom(closestEnemy) >= 160 && player.getAngleFrom(closestEnemy) <= 220)
            {
                System.out.println("Healed a patient!");
                enemyKilled = true;
                isColliding = true;
                world.deleteGameObject(closestEnemy);
                enemiesList.remove(closestEnemy);
                world.getScoreboard().increase();

            }else {
                System.out.println("Infected!");
                isColliding = true;
                world.getScoreboard().decrease();
            }
        }
        if(isColliding == true && closestEnemy.getDistanceFrom(player) != 0.0){
            isColliding = false;
            enemyKilled = false;
        }
    }

    /**
     * Restarts a previously set alarm unless the enemy limit has been exceeded
     */
    private void restartAlarm() {
        if (enemiesList.size() <= maxEnemies) {
            startAlarm();
        }
    }

    public List<Enemy> getAllEnemies()
    {
        return enemiesList;
    }

    /**
     * Acts when alarm is triggered
     * @param alarmName contains the alarm's name but isn't used here
     */
    @Override
    public void triggerAlarm(String alarmName) {
        if (world.getGameStarted()) {
            enemiesList.add(new Enemy(world, Enemy.Color.Yellow));
        }
        restartAlarm();
    }
}

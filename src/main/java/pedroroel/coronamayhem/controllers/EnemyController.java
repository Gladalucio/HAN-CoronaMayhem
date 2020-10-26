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
    private boolean collision = false;
    private static Enemy closestEnemy;
    private Boolean killed = false;

    public EnemyController(CoronaMayhem world) {
        this.world = world;
        enemiesList.add(new Enemy(world, Enemy.Color.Yellow, Enemy.SpawnSide.Left));
        enemiesList.add(new Enemy(world, Enemy.Color.Red, Enemy.SpawnSide.Right));

        startAlarm();
    }

    /**
     * Starts an alarm (timeout) for spawning an enemy
     */
    private void startAlarm() {
        Alarm alarm = new Alarm("enemy", spawnDelay);
        alarm.addTarget(this);
        alarm.start();
    }

    /**
     * returns true if collision is currently happening between player and enemy
     * @return Boolean
     */
    public boolean getCollision()
    {
        return collision;
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
     * returns true when the player colides with an enemy at the right angle to kill it
     * @return boolean
     */
    public Boolean getKilled()
    {
        return killed;
    }
    /**
     *
     *
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
        if(collision == false && closestEnemy.getDistanceFrom(player) == 0.0)
        {
            if(player.getAngleFrom(closestEnemy) >= 160 && player.getAngleFrom(closestEnemy) <= 220)
            {
                killed = true;
                collision = true;
                world.deleteGameObject(closestEnemy);
                enemiesList.remove(closestEnemy);
                System.out.println("healed that sick SoaB!");

            }else {
                System.out.println("infected by that SoaB!");
                collision = true;
            }
        }
        if(collision == true && closestEnemy.getDistanceFrom(player) != 0.0){
            collision = false;
            killed = false;
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

    /**
     * Acts when alarm is triggered
     * @param alarmName contains the alarm's name but isn't used here
     */
    @Override
    public void triggerAlarm(String alarmName) {
        enemiesList.add(new Enemy(world, Enemy.Color.Yellow));
        restartAlarm();
    }
}

package pedroroel.coronamayhem.entities.controllers;

import nl.han.ica.oopg.alarm.Alarm;
import nl.han.ica.oopg.alarm.IAlarmListener;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.entities.Enemy;

import java.util.ArrayList;
import java.util.List;

public class EnemyController implements IAlarmListener {
    private CoronaMayhem world;
    private List<Enemy> enemiesList = new ArrayList<Enemy>();
    private float spawnDelay = 3;
    private int maxEnemies = 10;

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

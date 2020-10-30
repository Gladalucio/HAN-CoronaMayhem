package pedroroel.coronamayhem.controllers;

import nl.han.ica.oopg.alarm.Alarm;
import nl.han.ica.oopg.alarm.IAlarmListener;
import pedroroel.coronamayhem.CoronaMayhem;

public abstract class AlarmController implements IAlarmListener {
    protected final CoronaMayhem world;
    protected final String delayAlarmName = "delay";
    protected final String spawnAlarmName = "spawn";
    protected float spawnDelay = 10;
    protected int maxSpawned = 10;

    public AlarmController(CoronaMayhem world) {
        this.world = world;
    }

    public abstract void startAlarm();

    /**
     * Delays setting a new alarm so maxDrops can be honored
     */
    protected void delayAlarm() {
        Alarm alarm = new Alarm(delayAlarmName, spawnDelay);
        alarm.addTarget(this);
        alarm.start();
    }

    @Override
    public abstract void triggerAlarm(String alarmName);
}

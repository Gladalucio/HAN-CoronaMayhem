package pedroroel.coronamayhem.controllers;

import nl.han.ica.oopg.alarm.IAlarmListener;
import pedroroel.coronamayhem.CoronaMayhem;

public abstract class AlarmController implements IAlarmListener {
    protected final CoronaMayhem world;

    public AlarmController(CoronaMayhem world) {
        this.world = world;
    }

    public abstract void startAlarm();
    protected abstract void restartAlarm();
}

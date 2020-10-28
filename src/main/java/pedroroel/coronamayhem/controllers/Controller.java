package pedroroel.coronamayhem.controllers;

import nl.han.ica.oopg.alarm.IAlarmListener;
import pedroroel.coronamayhem.CoronaMayhem;

public abstract class Controller implements IAlarmListener {
    protected final CoronaMayhem world;

    public Controller(CoronaMayhem world) {
        this.world = world;
    }

    public abstract void startAlarm();

    protected abstract void restartAlarm();
}

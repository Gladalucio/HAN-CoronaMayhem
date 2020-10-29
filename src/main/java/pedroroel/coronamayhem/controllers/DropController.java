package pedroroel.coronamayhem.controllers;

import nl.han.ica.oopg.alarm.Alarm;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.entities.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DropController extends AlarmController {
    private final List<Drop> drops = new ArrayList<>();
    private final float spawnDelay = 5;
    private final float spawnChance = 0.75f;
//    private final float spawnChance = 0.01f;
    private final String dropAlarmName = "drop";
    private final String despawnAlarmName = "despawn";

    public DropController(CoronaMayhem world) {
        super(world);
    }

    public List<Drop> getDrops() {
        return drops;
    }

    @Override
    public void startAlarm() {
        Alarm maskAlarm = new Alarm(dropAlarmName, spawnDelay);
        maskAlarm.addTarget(this);
        maskAlarm.start();

        if (drops.size() > 0) {
            Alarm despawnAlarm = new Alarm(despawnAlarmName, spawnDelay);
            despawnAlarm.addTarget(this);
            despawnAlarm.start();
        }
    }

    @Override
    protected void restartAlarm() {
        startAlarm();
    }

    @Override
    public void triggerAlarm(String alarmName) {
        if (!world.getGameStarted()) {
            restartAlarm();
            return;
        }

        if (alarmName.equals(despawnAlarmName)) {
            Iterator<Drop> iter = drops.iterator(); /* Regular for-loop gave a ConcurrentModificationException sometimes. This fixes that problem */
            while(iter.hasNext()) {
                Drop drop = iter.next();
                drop.despawn();
            }
            drops.clear();
            return;
        }

        if (shouldSpawn()) {
            spawn(new Mask(world));
        }
        if (shouldSpawn()) {
            spawn(new Virus(world));
        }

        restartAlarm();
    }

    private boolean shouldSpawn() {
        return Math.random() > Math.pow(spawnChance, returnSpawnChancePower());
    }

    private void spawn(Drop drop) {
        drop.spawn();
        drops.add(drop);
    }

    private float returnSpawnChancePower() {
        int score = world.getScoreboard().getScore();
        int chancePower = 1;

        if (score > 10) {
            chancePower = 3;
        } else if (score > 5) {
            chancePower = 2;
        }
        return chancePower;
    }

    public void despawn(Drop drop) {
        if (drops.contains(drop)) {
            drops.remove(drop);
        }
        if (world.getGameObjectItems().contains(drop)){
            world.deleteGameObject(drop);
        }
    }
}

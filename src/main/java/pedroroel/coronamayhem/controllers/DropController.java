package pedroroel.coronamayhem.controllers;

import nl.han.ica.oopg.alarm.Alarm;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.entities.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DropController extends AlarmController {
    private final List<Drop> drops = new ArrayList<>();

    public DropController(CoronaMayhem world) {
        super(world);
        spawnDelay = 10;
//        spawnDelay = 5; // For development only
        maxSpawned = 3;

    }

    public List<Drop> getDrops() {
        return drops;
    }

    @Override
    public void startAlarm() {
        Alarm maskAlarm = new Alarm(spawnAlarmName, spawnDelay);
        maskAlarm.addTarget(this);
        maskAlarm.start();
    }

    @Override
    public void triggerAlarm(String alarmName) {
        if (!world.getGameStarted()) {
            delayAlarm();
            return;
        }

        /* Despawn drops */
        if (drops.size() > 0) {
            Iterator<Drop> dropIter = drops.iterator();
            while (dropIter.hasNext()) {
                Drop drop = dropIter.next();
                dropIter.remove();
                drop.despawn();
            }
        }

        if (alarmName.equals(spawnAlarmName)) {
            if (drops.size() >= maxSpawned) {
                delayAlarm();
                return;
            }

            if (returnShouldSpawn()) {
                spawn(new Mask(world));
            }

            if (returnShouldSpawn()) {
                spawn(new Virus(world));
            }

            if (returnShouldSpawn()) {
                spawn(new Lockdown(world));
            }
        }

        startAlarm();
    }

    private boolean returnShouldSpawn() {
//        final float spawnChance = 0.75f;
        final float spawnChance = 0.01f; // For development only

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
            chancePower = 4;
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

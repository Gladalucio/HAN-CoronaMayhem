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
    private final String dropAlarmName = "drop";
    private final String despawnAlarmName = "despawn";

    public DropController(CoronaMayhem world) {
        super(world);
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
                iter.next().despawn();
            }
            drops.clear();
            System.out.println(drops.size());
            return;
        }

        int score = world.getScoreboard().getScore();
        int chancePower = 1;

        if (score > 10) {
            chancePower = 3;
        } else if (score > 5) {
            chancePower = 2;
        }

        boolean spawnMask = Math.random() > Math.pow(spawnChance, chancePower);
        System.out.println("spawnMask " + spawnMask);
        if (spawnMask) {
            Drop mask = new Mask(world);
            mask.spawn();
            drops.add(mask);
        }

        boolean spawnVirus = Math.random() > Math.pow(spawnChance, chancePower);
        System.out.println("spawnVirus " + spawnVirus);
        if(spawnVirus) {
            Drop virus = new Virus(world);
            virus.spawn();
            drops.add(virus);
        }

        restartAlarm();
    }

    /**
     * Checks and handles collision between any person and a drop
     * @param person an instance of the Person class, either the player or an enemy
     */
    public void dropCollisionOccurred(Person person, Drop drop) {
        if (drop.getDistanceFrom(person) == 0.0) {
            person.handleCollisionWith(drop);
            if (person instanceof Player) {

            }
        }
    }
}

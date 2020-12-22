package coronamayhem.controllers;

import nl.han.ica.oopg.alarm.Alarm;
import coronamayhem.CoronaMayhem;
import coronamayhem.entities.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Handles spawning and despawning of Drops.
 * extends AlarmController
 */
public class DropController extends AlarmController {
    private final List<Drop> drops = new ArrayList<>();

    public DropController(CoronaMayhem world) {
        super(world);
        spawnDelay = 10;
        maxSpawned = 3;
    }

    /**
     * Returns a list of all current drops
     * @return list of drops
     */
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

        /* If alarmName == spawnAlarmName, consider spawning more
        * If not, skip this and continue to "startAlarm()" */
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

    /**
     * Generates yes/no if a new Drop should be spawned
     * @return boolean
     */
    private boolean returnShouldSpawn() {
        final float spawnChance = 0.75f;
//        final float spawnChance = 0.01f; // For development only

        /* If Math.random()'s value is higher than the above spawnChance to the power of "spawnChancePower", return true
        * Math.random() generates a value between 0 and 1
        * At a score of 1, the return statement would translate to:
        *   return Math.random() > Math.pow(0,75, 1) -> if Math.random() > 0,75, return true (25% chance)
        * At a score of > 5, the return statement would translate to:
        *   return Math.random() > Math.pow(0,75, 2) -> if Math.random() > 0,5625, return true (~45% chance)
        * At a score of > 10, the return statement would translate to:
        *   return Math.random() > Math.pow(0,75, 4) -> if Math.random() > 0,31640625, return true (~70% chance) */
        return Math.random() > Math.pow(spawnChance, returnSpawnChancePower());
    }

    /**
     * Returns the power for "spawnChance". The higher the score, the higher the power thus the higher the spawn chance
     * @return float spawnChancePower
     */
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

    /**
     * Spawns a new drop and adds it to the drops list
     * @param drop to be spawned
     */
    private void spawn(Drop drop) {
        drop.spawn();
        drops.add(drop);
    }

    /**
     * Despawns an existing drop and removes it from the drops list
     * @param drop to be removed
     */
    public void despawn(Drop drop) {
        if (drops.contains(drop)) {
            drops.remove(drop);
        }
        if (world.getGameObjectItems().contains(drop)){
            world.deleteGameObject(drop);
        }
    }
}

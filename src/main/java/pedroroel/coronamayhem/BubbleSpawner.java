package pedroroel.coronamayhem;

import nl.han.ica.oopg.alarm.Alarm;
import nl.han.ica.oopg.alarm.IAlarmListener;
import nl.han.ica.oopg.sound.Sound;

import java.util.Random;

public class BubbleSpawner implements IAlarmListener {
    private float bubblesPerSecond;
    private Random random;
    private CoronaMayhem world;
    private Sound popSound;

    public BubbleSpawner(CoronaMayhem world, Sound popSound, float bubblesPerSecond) {
        this.bubblesPerSecond = bubblesPerSecond;
        this.world = world;
        this.popSound = popSound;
        random = new Random();
        startAlarm();
    }

    private void startAlarm() {
        Alarm alarm = new Alarm("New bubble", 1 / bubblesPerSecond);
        alarm.addTarget(this);
        alarm.start();
    }

    @Override
    public void triggerAlarm(String alarmName) {
        int bubbleSize = random.nextInt(10) + 10;
        Bubble b = new Bubble(bubbleSize, world, popSound);
        world.addGameObject(b, random.nextInt(world.width), world.height);
        startAlarm();
    }

}

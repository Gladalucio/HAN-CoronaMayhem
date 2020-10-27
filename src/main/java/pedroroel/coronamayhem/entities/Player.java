package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import pedroroel.coronamayhem.CoronaMayhem;

import java.util.List;

public class Player extends Person {
    final int size = 25;
    public int life = 1;
    public boolean started = false;

    /**
     * New player has his sprite, currentFrameIndex and gravity set
     * @param world contains a CoronaMayhem reference
     */
    public Player(CoronaMayhem world, int life) {

        super(world, new Sprite("src/main/java/pedroroel/coronamayhem/assets/images/doctor_mask.png"), 2);
        this.life = life;
        this.world = world;
        setCurrentFrameIndex(1);
        setFriction(0.025f);
    }
    public void increaseLife()
    {
        life = life +1;
    }
    public void reduceLife()
    {
        life = life -1;
    }

    /**
     * Controls player movement after a direction has been chosen in "keyPressed()"
     */
    @Override
    public void update() {
        if (getX() <= 0) {
            setxSpeed(0);
            setX(0);
        }
        if (getY() <= 0) {
            setySpeed(0);
            setY(0);
        }
        if (getX() >= world.width - size) {
            setxSpeed(0);
            setX(world.width - size);
        }
        if (getY() >= world.height - size) {
            setySpeed(0);
            setY(world.height - size);
        }
    }

    /**
     * handles the pressed keys and starts an action if it's implemented
     * @param keyCode pressed character keyCode
     * @param key pressed character key
     */
    @Override
    public void keyPressed(int keyCode, char key) {
        float jumpSpeed = 16;
        float directionalSpeed = 5;

        /* Direction: Up */
        if (key == 'w' && getDirection() != 0) {
            setDirectionSpeed(0, jumpSpeed);
        }

        /* Direction: Left */
        if (key == 'a') {
            setDirectionSpeed(270, directionalSpeed);
            setCurrentFrameIndex(0);
        }
        /* Direction: Right */
        if (key == 'd') {
            setDirectionSpeed(90, directionalSpeed);
            setCurrentFrameIndex(1);
        }

        if (keyCode == world.ENTER) {
            started = true;
        }
        if (keyCode == world.ESC && !started) {
            world.exit();
        }
    }
}

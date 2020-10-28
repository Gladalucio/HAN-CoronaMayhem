package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import pedroroel.coronamayhem.CoronaMayhem;

public class Player extends Person {
    private final int size = 25;

    /**
     * New player has his sprite, currentFrameIndex and gravity set
     * @param world contains a CoronaMayhem reference
     */
    public Player(CoronaMayhem world, int lives) {
        super(world, new Sprite(world.baseAssetPath + "images/player.png"), 4);
        this.lives = lives;
        this.world = world;
        setCurrentFrameIndex(1);
        setFriction(0.025f);
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

    @Override
    public void decreaseLives() {
        super.decreaseLives();
        if (lives < 0) {
            world.pause();
        }
    }

    public int returnCurrentFrameIndexOffset() {
        return lives > 0 ? 0 : 2;
    }

    /**
     * handles the pressed keys and starts an action if it's implemented
     * @param keyCode pressed character keyCode
     * @param key pressed character key
     */
    @Override
    public void keyPressed(int keyCode, char key) {
        /* Enter starts the game */
        if (keyCode == world.ENTER) {
            if (world.getGameStarted()) {
                world.pause();
            } else {
                world.resume();
            }
        }
        /* Escape closes the game */
        if (keyCode == world.ESC) {
            world.exit();
        }

        /* If the game hasn't started yet, controlling the player shouldn't be possible */
        if (!world.getGameStarted()) {
            return;
        }

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
    }
}

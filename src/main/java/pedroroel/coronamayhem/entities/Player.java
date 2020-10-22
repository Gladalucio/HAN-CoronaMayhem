package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import pedroroel.coronamayhem.CoronaMayhem;

import java.util.List;

public class Player extends Person {
    final int size = 25;

    /**
     * New player has his sprite, currentFrameIndex and gravity set
     * @param world contains a CoronaMayhem reference
     */
    public Player(CoronaMayhem world) {
        super(world, new Sprite("src/main/java/pedroroel/coronamayhem/assets/images/doctor_mask.png"), 2);
        setCurrentFrameIndex(1);
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
        if (key == 'w') { // up
            setDirectionSpeed(0, 3 * speed);
        }
        if (key == 'a') { // left
            setDirectionSpeed(270, (float) (1.5 * speed));
            setCurrentFrameIndex(0);
        }
        if (key == 'd') { // right
            setDirectionSpeed(90, (float) (1.5 * speed));
            setCurrentFrameIndex(1);
        }
    }
}

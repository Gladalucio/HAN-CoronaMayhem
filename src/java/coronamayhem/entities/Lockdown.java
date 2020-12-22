package coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import coronamayhem.CoronaMayhem;

/**
 *
 * extends Drop
 */
public class Lockdown extends Drop {
    public Lockdown(CoronaMayhem world) {
        super(world, new Sprite(world.baseAssetPath + "images/lockdown.png"));
    }

    /**
     * Spawns the Lockdown button on the same spot every time, instead of using the semi-random positioning function in Drop
     */
    @Override
    public void spawn() {
        float calcX = world.width / 2 * .93f; /* Center of the screen with a slight offset to the left (width of gameObject) */
        float calcY = world.height / 18 * 9; /* Based on the height of the screen divided by the number of tileRows */
        world.addGameObject(this, calcX, calcY);
    }
}

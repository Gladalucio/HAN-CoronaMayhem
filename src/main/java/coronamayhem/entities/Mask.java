package coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import coronamayhem.CoronaMayhem;

/**
 * Mask drops give the player an extra life and takes away one from enemies.
 * extends Drop
 */
public class Mask extends Drop {
    public Mask(CoronaMayhem world) {
        super(world, new Sprite(world.baseAssetPath + "images/mask.png"));
    }
}

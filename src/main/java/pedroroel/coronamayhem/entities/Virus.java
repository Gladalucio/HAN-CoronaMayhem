package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import pedroroel.coronamayhem.CoronaMayhem;

/**
 * Virus drops make enemies worse by one and damages the player by 1 life
 * extends Drop
 */
public class Virus extends Drop {
    public Virus(CoronaMayhem world) {
        super(world, new Sprite(world.baseAssetPath + "images/virus.png"));
    }
}

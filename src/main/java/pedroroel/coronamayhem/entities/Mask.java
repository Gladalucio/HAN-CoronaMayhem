package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import pedroroel.coronamayhem.CoronaMayhem;

public class Mask extends Drop {
    public Mask(CoronaMayhem world) {
        super(world, new Sprite(world.baseAssetPath + "images/mask.png"));
    }
}

package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import pedroroel.coronamayhem.CoronaMayhem;

public class Virus extends Drop {
    public Virus(CoronaMayhem world) {
        super(world, new Sprite(world.baseAssetPath + "images/virus.png"));
    }
}

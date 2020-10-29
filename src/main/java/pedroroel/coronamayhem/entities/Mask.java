package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import pedroroel.coronamayhem.CoronaMayhem;

public class Mask extends Drop {
    public Mask(CoronaMayhem world) {
        super(world, new Sprite(world.baseAssetPath + "images/mask.png"));
    }

    @Override
    public void spawn() {
        setCoords();
        world.addGameObject(this);
    }

    @Override
    public void despawn() {
        world.deleteGameObject(this);
    }

    @Override
    public void setCoords() {
        setX(125);
        setY(250);
    }
}

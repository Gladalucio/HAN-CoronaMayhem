package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import pedroroel.coronamayhem.CoronaMayhem;

public class Virus extends Drop {
    public Virus(CoronaMayhem world) {
        super(world, new Sprite(world.baseAssetPath + "images/virus.png"));
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
        setX(world.width - 125);
        setY(250);
    }
}

package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import pedroroel.coronamayhem.CoronaMayhem;

public abstract class Drop extends SpriteObject {
    protected final CoronaMayhem world;

    public Drop(CoronaMayhem world, Sprite sprite) {
        super(sprite);
        this.world = world;
    }

    public abstract void spawn();
    public abstract void despawn();
    public abstract void setCoords();

    @Override
    public void update() {}
}

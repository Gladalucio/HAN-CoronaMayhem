package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import pedroroel.coronamayhem.CoronaMayhem;

public class MaskDrop extends SpriteObject {
    private CoronaMayhem world;

    public MaskDrop(CoronaMayhem world) {
        super(new Sprite(world.baseAssetPath + "images/mask.png"));
        this.world = world;
    }

    private void spawn() {
        setX(200);
        setY(200);
        world.addGameObject(this);
    }

    @Override
    public void update() {

    }
}

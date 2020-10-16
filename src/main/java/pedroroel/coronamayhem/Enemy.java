package pedroroel.coronamayhem;

import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.Sprite;

public class Enemy extends AnimatedSpriteObject {
    private CoronaMayhem world;

    public Enemy(CoronaMayhem world) {
        this(new Sprite("src/main/java/pedroroel/coronamayhem/assets/images/enemy_yellow.png"));
        this.world = world;
    }

    private Enemy(Sprite sprite) {
        super(sprite, 2);
//        setxSpeed(-1);
    }

    @Override
    public void update() {
        if (getX() + getWidth() <= 0) {
            setX(world.width);
        }

    }
}

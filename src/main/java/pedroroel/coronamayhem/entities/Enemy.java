package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import pedroroel.coronamayhem.CoronaMayhem;

public class Enemy extends Person {
    public enum Color {
        Yellow,
        Orange,
        Red
    }
    private Color enemyType;

    public Enemy(CoronaMayhem world, Color enemyType) {
        this(new Sprite("src/main/java/pedroroel/coronamayhem/assets/images/enemy_yellow.png"));
        this.world = world;
    }

    private Enemy(Sprite sprite) {
        super(sprite, 2);
        setxSpeed(-1);
    }

    @Override
    public void update() {
        if (getX() + getWidth() <= 0) {
            setX(world.width);
        }
    }
}

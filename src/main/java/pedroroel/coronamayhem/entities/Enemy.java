package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import pedroroel.coronamayhem.CoronaMayhem;

public class Enemy extends Person {
    public enum Color {
        Yellow,
        Orange,
        Red
    }
    public enum SpawnSide {
        Right,
        Left
    }

    private Color enemyColor;
    private SpawnSide spawnSide;

    public Enemy(CoronaMayhem world, Color enemyColor) {
        super(world, new Sprite("src/main/java/pedroroel/coronamayhem/assets/images/enemy_" + enemyColor.toString().toLowerCase() + ".png"));
        this.enemyColor = enemyColor;
        determineSpawnSide();
        spawn();
    }

    public Enemy(CoronaMayhem world, Color enemyColor, SpawnSide spawnSide) {
        super(world, new Sprite("src/main/java/pedroroel/coronamayhem/assets/images/enemy_" + enemyColor.toString().toLowerCase() + ".png"));
        this.enemyColor = enemyColor;
        this.spawnSide = spawnSide;
        spawn();
    }

    @Override
    public void update() {
        if (getX() + getWidth() <= 0) {
            setX(world.width - 100);
        } else if (getX() >= world.width) {
            setX(35);
        }
    }

    private void determineSpawnSide() {
        spawnSide = Math.random() < 0.5 ? SpawnSide.Left : SpawnSide.Right;
    }

    private void spawn() {
        if (spawnSide == SpawnSide.Left) {
            setCurrentFrameIndex(1);
            setxSpeed((float) (speed + Math.random() / 2));
            setX(35);
        } else {
            setCurrentFrameIndex(0);
            setxSpeed(-(float) (speed + Math.random() / 2));
            setX(world.width - 100);
        }

        setY(19);
        world.addGameObject(this);
    }
}

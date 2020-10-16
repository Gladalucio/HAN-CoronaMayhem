package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import pedroroel.coronamayhem.CoronaMayhem;

import java.util.Random;

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
        super(world, new Sprite("src/main/java/pedroroel/coronamayhem/assets/images/enemy_yellow.png"));
        this.enemyColor = enemyColor;
        determineSpawnSide();
        setxSpeed(speed);
    }

    @Override
    public void update() {
        if (getX() + getWidth() <= 0) {
            setX(world.width);
        }
    }

    private void determineSpawnSide() {
        Random r = new Random();
        int spawnSideIndex = 0;
        setCurrentFrameIndex(spawnSideIndex);
        if (spawnSideIndex == 1) {
            this.spawnSide = SpawnSide.Left;
            setxSpeed(speed);
        } else {
            this.spawnSide = SpawnSide.Right;
            setxSpeed(-speed);
        }
//        this.spawnSide = r.nextInt(1) == 1 ? SpawnSide.Left : SpawnSide.Right;
    }
}

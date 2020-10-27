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
        super(world, new Sprite(world.baseImagePath + "enemy_" + enemyColor.toString().toLowerCase() + ".png"), 2);
        this.enemyColor = enemyColor;
        determineSpawnSide();
        spawn();
    }

    public Enemy(CoronaMayhem world, Color enemyColor, SpawnSide spawnSide) {
        super(world, new Sprite(world.baseImagePath + "enemy_" + enemyColor.toString().toLowerCase() + ".png"), 2);
        this.enemyColor = enemyColor;
        this.spawnSide = spawnSide;
        spawn();
    }

    /**
     * Currently used to check collision with walls.
     * Allows enemies to go trough said walls and appear on the opposite side
     */
    @Override
    public void update() {
        if (getX() + getWidth() <= 0) { /* Hits left wall, reappears on the other side */
            setX(world.width - 100);
            checkForRespawn();
        } else if (getX() >= world.width) { /* Hits right wall, reappears on the other side */
            setX(35);
            checkForRespawn();
        }
    }

    private void determineSpawnSide() {
        spawnSide = Math.random() < 0.5 ? SpawnSide.Left : SpawnSide.Right;
    }

    /**
     * Spawns an enemy facing and moving the correct way after deciding it's spawn side
     */
    private void spawn() {
        if (spawnSide == SpawnSide.Left) {
            setCurrentFrameIndex(1);
            entitySpeed = (float) (entitySpeed + Math.random() / 2);
            setX(35);
        } else if (spawnSide == SpawnSide.Right){
            setCurrentFrameIndex(0);
            entitySpeed = (float) -(entitySpeed + Math.random() / 2);
            setX(world.width - 100);
        } else {
            determineSpawnSide();
            spawn();
            return;
        }

        if (world.getGameStarted()) {
            setxSpeed(entitySpeed);
        }
        setY(19);
        world.addGameObject(this);
    }

    /**
     * Checks if character has to be respawned after reaching a wall and coming out on the other side
     * If an enemy is on the bottom row, it should be respawned at the very top row
     */
    private void checkForRespawn() {
        if (getY() > (world.height - this.height * 1.5)) {
            setySpeed(0);
            setY(19);
        }
    }
}

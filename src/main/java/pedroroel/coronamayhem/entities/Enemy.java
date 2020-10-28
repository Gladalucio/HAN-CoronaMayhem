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
        super(world, new Sprite(world.baseAssetPath + "images/enemies.png"), 6);
        this.enemyColor = enemyColor;
        determineSpawnSide();
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

    @Override
    public void decreaseLives() {
        super.decreaseLives();
        if (lives < 0) {
            world.getEnemyCtrl().removeEnemy(this);
        }
    }

    public int returnCurrentFrameIndexOffset() {
        switch(lives) {
            case 2:
                enemyColor = Color.Red;
                return 4;
            case 1:
                enemyColor = Color.Orange;
                return 2;
            default:
                enemyColor = Color.Yellow;
                return 0;
        }
    }

    private void determineSpawnSide() {
        spawnSide = Math.random() < 0.5 ? SpawnSide.Left : SpawnSide.Right;
    }

    /**
     * Spawns an enemy facing and moving the correct way after deciding it's spawn side
     */
    private void spawn() {
        switch(enemyColor) {
            case Red:
                lives = 2;
                break;
            case Orange:
                lives = 1;
                break;
            case Yellow:
                lives = 0;
                break;
        }

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

        setxSpeed(entitySpeed);
        setY(19);
        world.addGameObject(this);
        setCurrentFrameIndex(getCurrentFrameIndex());
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

package coronamayhem.entities;

import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import coronamayhem.CoronaMayhem;

/**
 * Patients are the enemies
 * extends Person
 */
public class Enemy extends Person {
    private Color enemyColor;
    private SpawnSide spawnSide;
    private final Sound doorSound = new Sound(world, world.baseAssetPath + "sounds/enemy_door.mp3");
    private final Sound healedSound = new Sound(world, world.baseAssetPath + "sounds/enemy_healed.mp3");

    public enum Color {
        Yellow,
        Orange,
        Red
    }
    public enum SpawnSide {
        Right,
        Left
    }

    public Enemy(CoronaMayhem world, Color enemyColor) {
        super(world, new Sprite(world.baseAssetPath + "images/enemies.png"), 6);
        this.enemyColor = enemyColor;
        determineSpawnSide();
        spawn();
    }

    /**
     * Used to allow enemies to go trough walls and appear on the opposite side
     */
    @Override
    public void update() {
        if (getX() + getWidth() <= 0) {
            /* Hits left wall, reappears on the other side */
            setX(world.width - 100);
            checkForRespawn();
        } else if (getX() >= world.width) {
            /* Hits right wall, reappears on the other side */
            setX(35);
            checkForRespawn();
        }
    }

    @Override
    public void increaseLives() {
        if (lives < 2) {
            super.increaseLives();
        }
    }

    @Override
    public void decreaseLives() {
        super.decreaseLives();
        playHealedSound();
        world.getScoreboard().increase();
        /* Removing the enemy after being killed was moved to "checkCollision" in the EnemyController
        * This because of the fact I couldn't solve the ConcurrentModificationException any other way */
    }

    /**
     * Returns the offset the currentFrameOffset needs to show the correct sprite (see the explanation for this method in Person.java)
     * EnemyColor and currentFrameIndexOffset are based on the lives the enemy has left
     */
    @Override
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

    @Override
    public void handleCollisionWith(GameObject object) {
        if (object instanceof Mask) {
            /* Masks decrease the lives of an enemy by 1 */
//            System.out.println("Enemy " + this.enemyColor + " hit a mask!");
            decreaseLives();
        } else if (object instanceof Virus) {
            /* Viruses make the patient more sick, so increases lives by 1 */
//            System.out.println("Enemy " + this.enemyColor + " hit a virus!");
            increaseLives();
        }
    }

    /**
     * Sets a spawn side for the newly created enemies
     */
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
            /* If no spawnside was set, executes "determineSpawnSide" and returns to this function */
            determineSpawnSide();
            spawn();
            return;
        }

        playDoorSound();
        setxSpeed(entitySpeed);
        /* Sets Y to the top floor */
        setY(19);
        world.addGameObject(this);
    }

    /**
     * Checks if character has to be respawned after reaching a wall and coming out on the other side
     * If an enemy is on the bottom row, it should be respawned at the very top row
     */
    private void checkForRespawn() {
        if (getY() > (world.height - this.height * 1.5)) {
            playDoorSound();
            setySpeed(0);
            setY(19);
        }
    }

    /**
     * Plays the "door" sound effect
     */
    public void playDoorSound() {
        doorSound.rewind();
        doorSound.play();
    }

    /**
     * Plays the "healed" sound effect
     */
    public void playHealedSound() {
        healedSound.rewind();
        healedSound.play();
    }
}

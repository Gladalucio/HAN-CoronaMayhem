package coronamayhem.entities;

import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import coronamayhem.CoronaMayhem;
import coronamayhem.controllers.CollisionController;

import java.util.List;

/**
 * Player behavior is defined here
 * extends Person
 */
public class Player extends Person {
    private final Sound hitSound = new Sound(world, world.baseAssetPath + "sounds/player_hit.mp3");

    /**
     * New player has his sprite, currentFrameIndex and gravity set
     * @param world contains a CoronaMayhem reference
     */
    public Player(CoronaMayhem world, int lives) {
        super(world, new Sprite(world.baseAssetPath + "images/player.png"), 4);
        this.lives = lives;
        this.world = world;
        setCurrentFrameIndex(1);
        setFriction(0.025f);
    }

    /**
     * Controls player movement after a direction has been chosen in "keyPressed()"
     */
    @Override
    public void update() {
        final int size = 25;

        if (getX() <= 0) {
            setxSpeed(0);
            setX(0);
        }
        if (getY() <= 0) {
            setySpeed(0);
            setY(0);
        }
        if (getX() >= world.width - size) {
            setxSpeed(0);
            setX(world.width - size);
        }
        if (getY() >= world.height - size) {
            setySpeed(0);
            setY(world.height - size);
        }
    }

    @Override
    public void increaseLives() {
        if (lives < 1) {
            super.increaseLives();
        }
    }

    @Override
    public void decreaseLives() {
        playHitSound();
        super.decreaseLives();
        world.getScoreboard().decrease();
        if (lives < 0) {
            world.pause();
        }
    }

    /**
     * Plays "hit" sound effect
     */
    private void playHitSound() {
        hitSound.rewind();
        hitSound.play();
    }

    @Override
    public int returnCurrentFrameIndexOffset() {
        return lives > 0 ? 0 : 2;
    }

    /**
     * Checks player collision with both enemies and drops
     * @param collisionCtrl Easy reference to the CollisionController
     */
    public void checkCollisionOccurred(CollisionController collisionCtrl) {
        /* maxDist, minDist, closestDist and collisionHappened are used to check for valid collision and prevent bouncing */
        final float maxDist = 10000;
        final float minDist = 20;
        float closestDist = maxDist;
        boolean collisionHappened = false;

        /* Check for collision with Enemies */
        List<Enemy> enemies = world.getEnemyCtrl().getEnemies();
        /* Only check if there are more than 0 enemies */
        if (enemies.size() > 0) {
            for (Enemy enemy: enemies) {
                float dist = collisionCtrl.returnDistance(this, enemy);
                if (dist < closestDist) {
                    closestDist = dist;
                }

                boolean collidedWithEnemy = collisionCtrl.hasCollisionOccurred(this, enemy);
                if (collidedWithEnemy && !isColliding) {
                    isColliding = collisionHappened = true;
                    handleCollisionWith(enemy);
                    break; /* Only one enemy can be collided with at a time, so there is no need to continue the loop */
                }
            }
        }

        /* Resets the enemy's "isColliding" to false when the collision with any drops is over */
        if (!collisionHappened && isColliding && closestDist > minDist && closestDist != maxDist) {
            isColliding = false;
        }

        /* Check for collision with Drops */
        List<Drop> drops = world.getDropCtrl().getDrops();
        /* Only check if there are more than 0 drops*/
        if (drops.size() > 0) {
            for (Drop drop: drops) {
                boolean collidedWithDrop = collisionCtrl.hasCollisionOccurred(this, drop);
                if (collidedWithDrop) {
                    handleCollisionWith(drop);
                    break; /* Only one drop can be collided with at a time, so it makes sense to break here */
                }
            }
        }
    }

    @Override
    public void handleCollisionWith(GameObject object) {
        if (object instanceof Enemy) {
            /* Hit an enemy, check if the hit was on it's head */
            if (this.getAngleFrom(object) >= 160 && this.getAngleFrom(object) <= 220) {
                /* Hit the enemy on the head */
//                System.out.println("Healed!");
                ((Enemy)object).decreaseLives();
//                world.getScoreboard().increase();
            } else {
                /* Didn't hit the enemy on the head so you get infected */
//                System.out.println("Infected!");
                decreaseLives();
//                world.getScoreboard().decrease();
            }
        } else if (object instanceof Drop) {
            /* Hit some kind of drop */
//            System.out.println("Player hit a drop!");
            if (object instanceof Mask) {
                /* Masks increase the lives of the player by 1 */
                increaseLives();
//                System.out.println("Drop is a Mask!");
            } else if (object instanceof Virus) {
                /* Viruses take away 1 health from the player */
                decreaseLives();
//                System.out.println("Drop is a Virus!");
            } else if (object instanceof Lockdown) {
                /* Lockdown button hits all current enemies by 1 */
                world.getEnemyCtrl().decreaseLivesBy(1);
//                System.out.println("Lockdown button hit!");
            }
            /* All Drops are despawned after being hit by the player */
            world.getDropCtrl().despawn(((Drop)object));
        }
    }

    /**
     * Handles key presses and starts an action if it's implemented
     * @param keyCode pressed key keyCode
     * @param key pressed key
     */
    @Override
    public void keyPressed(int keyCode, char key) {
        /* Esc always closes the window, so doesn't need to be included here */

        /* Enter starts the game */
        if (keyCode == world.ENTER) {
            if (world.getGameStarted()) {
                world.pause();
            } else {
                world.resume();
            }
        }

        /* If the game hasn't started yet, controlling the player shouldn't be possible */
        if (!world.getGameStarted()) {
            return;
        }

        final float jumpSpeed = 16;
        final float directionalSpeed = 5;

        /* Direction: Up */
        if (key == 'w' && getDirection() != 0) {
            setDirectionSpeed(0, jumpSpeed);
        }

        /* Direction: Left */
        if (key == 'a') {
            setDirectionSpeed(270, directionalSpeed);
            /* Sets the direction the player sprite is looking */
            setCurrentFrameIndex(0);
        }
        /* Direction: Right */
        if (key == 'd') {
            setDirectionSpeed(90, directionalSpeed);
            /* Sets the direction the player sprite is looking */
            setCurrentFrameIndex(1);
        }
    }
}

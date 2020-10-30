package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.controllers.CollisionController;

import java.util.List;

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
        if (lives < 0) {
            world.pause();
        }
    }

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
        /* Checks if no more collisions are happening. If not, resets "isColliding" */
        final float maxDist = 10000;
        final float minDist = 5;
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
                    break; /* Only one enemy can be collided with, so it makes sense to break here */
                }
            }
        }

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
                    break; /* Only one drop can be collided with, so it makes sense to break here */
                }
            }
        }
    }

    /**
     * Handles collision with a certain game object based on what it is
     * @param object the collided game object
     */
    @Override
    public void handleCollisionWith(GameObject object) {
        if (object instanceof Enemy) {
            /* Hit an enemy */
//            System.out.println("Player hit an enemy!");
            if (this.getAngleFrom(object) >= 160 && this.getAngleFrom(object) <= 220) {
//                System.out.println("Healed!");
                ((Enemy)object).decreaseLives();
                world.getScoreboard().increase();
            } else {
//                System.out.println("Infected!");
                decreaseLives();
                world.getScoreboard().decrease();
            }
        } else if (object instanceof Drop) {
            /* Hit some kind of drop */
//            System.out.println("Player hit a drop!");
            if (object instanceof Mask) {
                increaseLives();
//                System.out.println("Drop is a Mask!");
            } else if (object instanceof Virus) {
                decreaseLives();
//                System.out.println("Drop is a Virus!");
            }
            System.out.println("Despawned!");
            world.getDropCtrl().despawn(((Drop)object));
        }
    }

    /**
     * handles the pressed keys and starts an action if it's implemented
     * @param keyCode pressed character keyCode
     * @param key pressed character key
     */
    @Override
    public void keyPressed(int keyCode, char key) {
        /* Enter starts the game */
        if (keyCode == world.ENTER) {
            if (world.getGameStarted()) {
                world.pause();
            } else {
                world.resume();
            }
        }
        /* Escape closes the game */
        if (keyCode == world.ESC) {
            world.exit();
        }

        /* If the game hasn't started yet, controlling the player shouldn't be possible */
        if (!world.getGameStarted()) {
            return;
        }

        float jumpSpeed = 16;
        float directionalSpeed = 5;

        /* Direction: Up */
        if (key == 'w' && getDirection() != 0) {
            setDirectionSpeed(0, jumpSpeed);
        }

        /* Direction: Left */
        if (key == 'a') {
            setDirectionSpeed(270, directionalSpeed);
            setCurrentFrameIndex(0);
        }
        /* Direction: Right */
        if (key == 'd') {
            setDirectionSpeed(90, directionalSpeed);
            setCurrentFrameIndex(1);
        }
    }
}

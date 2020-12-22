package coronamayhem.entities;

import nl.han.ica.oopg.collision.CollidedTile;
import nl.han.ica.oopg.collision.CollisionSide;
import nl.han.ica.oopg.collision.ICollidableWithTiles;
import nl.han.ica.oopg.exceptions.TileNotFoundException;
import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import coronamayhem.CoronaMayhem;
import coronamayhem.objects.GameTile;
import processing.core.PVector;

import java.util.List;

/**
 * Players and Enemies share attributes in Person
 */
public abstract class Person extends AnimatedSpriteObject implements ICollidableWithTiles {
    protected CoronaMayhem world;
    protected float entitySpeed = 2f;
    protected int lives = 1;
    protected boolean isColliding = false;

    public Person(CoronaMayhem world, Sprite sprite, int totalFrames) {
        super(sprite, totalFrames);
        this.world = world;
        setGravity(0.3f);
    }

    /**
     * Returns the current amount of lives
     * @return lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Returns the current state of "isColliding"
     * @return boolean isColliding
     */
    public boolean getIsColliding() {
        return isColliding;
    }

    /**
     * Sets a new state for "isColliding"
     * @param value the new "isColliding" value
     */
    public void setIsColliding(boolean value) {
        isColliding = value;
    }

    /**
     * Sets the currentFrameIndex
     * @param currentFrameIndex the new currentFrameIndex
     * currentFrameIndex is the frame currently shown for entities with more than one sprite
     */
    @Override
    public void setCurrentFrameIndex(int currentFrameIndex) {
        /* currentFrameIndex can only be 0 or 1. Modulus filters it down to either 0 or 1 */
        currentFrameIndex %= 2;
        /*  */
        super.setCurrentFrameIndex(currentFrameIndex + returnCurrentFrameIndexOffset());
    }

    /**
     * returns the currentFrameIndexOffset
     * This offset is necessary to show the correct sprite since all sprites are in one file but cut into multiple pieces
     * A sprite looking left would be "currentFrameIndex == 0", a sprite looking right would be "currentFrameIndex == 1"
     * If there is a different version of a sprite (example: The player with and without a mask), the offset adds itself to
     *  the above "setCurrentFrameIndex".
     * A player with mask looking left would be "setCurrentFrameIndex(0 + 0)" so frame 0 is chosen
     * A player without a mask looking left would be "setCurrentFrameIndex(0 + 2)" so frame 2 is chosen
     * @return currentFrameIndexOffset
     */
    public abstract int returnCurrentFrameIndexOffset();

    /**
     * Increases the amount of lives, changes the sprite currentFrameIndex and updates the scoreboard
     */
    public void increaseLives() {
        lives += 1;
        setCurrentFrameIndex(getCurrentFrameIndex());
        world.getScoreboard().update();
    }

    /**
     * Increases the amount of lives, changes the sprite currentFrameIndex and updates the scoreboard
     */
    public void decreaseLives() {
        lives -= 1;
        setCurrentFrameIndex(getCurrentFrameIndex());
        world.getScoreboard().update();
    }

    /**
     * Stops the Y-speed going through the roof.
     * The y-speed of the entity is 0,66th so it's harder to notice
     */
    protected void limitYSpeed() {
        if (getySpeed() > 20) {
            setySpeed(getySpeed() / 3 * 2);
        }
    }

    /**
     * Handles tile collisions between any Person and the game world
     * @param collidedTiles array of collided tiles
     */
    @Override
    public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
        for (CollidedTile ct : collidedTiles) {
            if (ct.getTile() instanceof GameTile) {
                try {
                    limitYSpeed();
                    PVector vector = world.getTileMap().getTilePixelLocation(ct.getTile());

                    if (CollisionSide.TOP.equals(ct.getCollisionSide())) {
                        setY(vector.y - getHeight());
                    }
                } catch (TileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Handles collision with a certain game object based on what it is
     * @param object the collided with game object
     */
    public abstract void handleCollisionWith(GameObject object);
}

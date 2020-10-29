package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.collision.CollidedTile;
import nl.han.ica.oopg.collision.CollisionSide;
import nl.han.ica.oopg.collision.ICollidableWithTiles;
import nl.han.ica.oopg.exceptions.TileNotFoundException;
import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.controllers.CollisionController;
import pedroroel.coronamayhem.objects.GameTile;
import processing.core.PVector;

import java.util.List;

public abstract class Person extends AnimatedSpriteObject implements ICollidableWithTiles {
    protected CoronaMayhem world;
    protected float entitySpeed = 2f;
    protected int lives = 1;


    public Person(CoronaMayhem world, Sprite sprite, int totalFrames) {
        super(sprite, totalFrames);
        this.world = world;
        setGravity(0.3f);
    }

    public int getLives() {
        return lives;
    }

    @Override
    public void setCurrentFrameIndex(int currentFrameIndex) {
        currentFrameIndex %= 2;
        super.setCurrentFrameIndex(currentFrameIndex + returnCurrentFrameIndexOffset());
    }

    public void increaseLives() {
        lives += 1;
        setCurrentFrameIndex(getCurrentFrameIndex());
        world.getScoreboard().update();
    }

    public void decreaseLives() {
        lives -= 1;
        setCurrentFrameIndex(getCurrentFrameIndex());
        world.getScoreboard().update();
    }

    public abstract int returnCurrentFrameIndexOffset();

    /**
     * Function added to stop the Y-speed going through the roof.
     * The y-speed of the entity is 0,66th so it's harder to notice
     * */
    protected void limitYSpeed() {
        if (getySpeed() > 20) {
            setySpeed(getySpeed() / 3 * 2);
        }
    }

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

    public abstract void handleCollisionWith(GameObject object);
}

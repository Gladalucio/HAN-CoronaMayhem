package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.collision.CollidedTile;
import nl.han.ica.oopg.collision.CollisionSide;
import nl.han.ica.oopg.collision.ICollidableWithTiles;
import nl.han.ica.oopg.exceptions.TileNotFoundException;
import nl.han.ica.oopg.objects.AnimatedSpriteObject;
import nl.han.ica.oopg.objects.Sprite;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.tiles.GameTile;
import processing.core.PVector;

import java.util.List;

public abstract class Person extends AnimatedSpriteObject implements ICollidableWithTiles {
    protected CoronaMayhem world;
    protected float speed = 3f;

    public Person(CoronaMayhem world, Sprite sprite, int totalFrames) {
        super(sprite, totalFrames);
        this.world = world;
        setGravity(0.1f);
    }

    /**
     * Function added to stop the Y-speed going through the roof.
     * The y-speed of the entity is 0,66th so it's harder to notice
     * */

    protected void resetYSpeed() {
        if (getySpeed() > 5) {
            setySpeed(getySpeed() / 3 * 2);
        }
    }

    @Override
    public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
        for (CollidedTile ct : collidedTiles) {
            if (ct.getTile() instanceof GameTile) {
                try {
                    resetYSpeed();
                    PVector vector = world.getTileMap().getTilePixelLocation(ct.getTile());

                    if (CollisionSide.TOP.equals(ct.getCollisionSide())) {
                        setY(vector.y - getHeight());

                        // On collision and moving downward, give enemies a random direction to walk in
                        if (getDirection() == 180 && this instanceof Enemy) {
                            boolean goRight = Math.random() < 0.5;
                            setDirectionSpeed(goRight ? 90 : 270, speed);
                            setCurrentFrameIndex(goRight ? 1 : 0);
                        }
                    } else if (CollisionSide.BOTTOM.equals(ct.getCollisionSide())) {
//                        setDirectionSpeed(180, speed);
                    }
                    // Watch out: Adding "CollisionSide.RIGHT" and ".LEFT" results in weird behavior
                } catch (TileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

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
    protected int speed = 2;
//    protected float x, y, vx, vy, ax, ay = 0.2f;

    public Person(CoronaMayhem world, Sprite sprite, int totalFrames) {
        super(sprite, totalFrames);
        this.world = world;
    }

    public Person(CoronaMayhem world, Sprite sprite) {
        super(sprite,2 );
        this.world = world;
    }

    @Override
    public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
        PVector vector;

        for (CollidedTile ct : collidedTiles) {
            if (ct.getTile() instanceof GameTile) {
                if (CollisionSide.TOP.equals(ct.getCollisionSide())) {
                    try {
                        vector = world.getTileMap().getTilePixelLocation(ct.getTile());
                        setY(vector.y - getHeight());
                    } catch (TileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import pedroroel.coronamayhem.CoronaMayhem;

import java.util.Random;

public abstract class Drop extends SpriteObject {
    protected final CoronaMayhem world;

    public Drop(CoronaMayhem world, Sprite sprite) {
        super(sprite);
        this.world = world;
    }

    public void spawn() {
        setPredeterminedCoords();
        world.addGameObject(this);
    }

    public void despawn() {
        if (world.getGameObjectItems().contains(this)) {
            world.deleteGameObject(this);
        }
    }

    protected void setPredeterminedCoords() {
        int xBlock = world.width / 24;
        int yBlock = world.height / 18;
        float[] yLocations = {
                yBlock * 1.5f,
                yBlock * 7.5f,
                yBlock * 11.5f,
                yBlock * 15.5f
        };

        setY(yLocations[returnRandomInt(yLocations.length)]);
        if (getY() > yBlock * 14 || getY() < yBlock * 2) {
            setX(returnRandomInt(1 + world.width));
        } else {
            boolean spawnLeft = returnRandomInt(2) < 1;
            if (spawnLeft) {
                setX(returnRandomInt(xBlock * 8, xBlock));
            } else {
                setX(returnRandomInt(xBlock * 16, xBlock * 23));
            }

        }
    }

//    public abstract void setCoords();

    protected int returnRandomInt(int bound) {
        Random r = new Random();
        return r.nextInt(bound);
    }

    protected int returnRandomInt(int bound, int start) {
        Random r = new Random();
        return r.nextInt(bound) + start;
    }

    @Override
    public void update() {}
}

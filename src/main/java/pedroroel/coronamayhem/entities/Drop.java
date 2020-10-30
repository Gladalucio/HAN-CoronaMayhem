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
        System.out.println("Drop coords: " + getX() + ", " + getY());
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
            int topX = returnRandomInt(world.getWidth() - xBlock * 2, xBlock);
            setX(topX);
        } else {
            boolean spawnLeft = returnRandomInt(2) < 1;
            if (spawnLeft) {
                int topXLeft = returnRandomInt(xBlock * 8, xBlock);
                setX(topXLeft);
            } else {
                int topXRight = returnRandomInt(xBlock * 8, xBlock * 14);
                setX(topXRight);
            }
        }
    }

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

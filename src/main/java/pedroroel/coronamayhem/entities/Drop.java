package pedroroel.coronamayhem.entities;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import pedroroel.coronamayhem.CoronaMayhem;

import java.util.Random;

/**
 * Any kind of temporary and/or stationary item is considered a Drop and extends from here
 */
public abstract class Drop extends SpriteObject {
    protected final CoronaMayhem world;

    public Drop(CoronaMayhem world, Sprite sprite) {
        super(sprite);
        this.world = world;
    }

    /**
     * Spawns itself
     */
    public void spawn() {
        setPredeterminedCoords();
//        System.out.println("Drop coords: " + getX() + ", " + getY());
        world.addGameObject(this);
    }

    /**
     * Despawns itself
     */
    public void despawn() {
        if (world.getGameObjectItems().contains(this)) {
            world.deleteGameObject(this);
        }
    }

    /**
     * Sets a Y-value from a predetermined list
     * Generates and sets an X-value based on the chosen Y
     */
    protected void setPredeterminedCoords() {
        int xBlock = world.width / 24;
        int yBlock = world.height / 18;
        /* Predetermined heights (Y-values) based on world tiles */
        float[] yLocations = {
                yBlock * 1.5f,
                yBlock * 7.5f,
                yBlock * 11.5f,
                yBlock * 15.5f
        };

        /* Sets an Y-location based on the outcome of "returnRandomInt" */
        setY(yLocations[returnRandomInt(yLocations.length)]);

        /* Sets an X-value based on the Y-location
        * if the Y is at the top or bottom, generate any kind of X */
        if (getY() > yBlock * 14 || getY() < yBlock * 2) {
            int topX = returnRandomInt(world.getWidth() - xBlock * 2, xBlock);
            setX(topX);
        } else {
            /* if the Y is somewhere in the middle, generate an X only on the right or left sides */
            boolean spawnLeft = returnRandomInt(2) < 1;
            setX(returnRandomInt(xBlock * 6, spawnLeft ? xBlock : xBlock * 16));
        }
    }

    /**
     * Returns a random int up to the given bound
     * @param bound the maximum value of the generated int
     * @return the random int
     */
    protected int returnRandomInt(int bound) {
        Random r = new Random();
        return r.nextInt(bound);
    }

    /**
     * Returns a random int between the start and bound
     * @param bound the maximum value of the generated int
     * @param start the start value of the generated int
     * @return the random int
     */
    protected int returnRandomInt(int bound, int start) {
        Random r = new Random();
        return r.nextInt(bound) + start;
    }

    /**
     * Updates the class. Isn't used here, instead opting for a more global approach.
     */
    @Override
    public void update() {}
}

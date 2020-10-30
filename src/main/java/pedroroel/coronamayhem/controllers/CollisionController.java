package pedroroel.coronamayhem.controllers;

import nl.han.ica.oopg.objects.GameObject;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.entities.*;

/**
 * Starts the collision checks and is used to check if collisions have occurred
 */
public class CollisionController {
    private final CoronaMayhem world;

    public CollisionController(CoronaMayhem world) {
        this.world = world;
    }

    /**
     * Starts all collision checks
     */
    public void checkCollisions() {
        world.getPlayer().checkCollisionOccurred(this);
        world.getEnemyCtrl().checkCollisionOccurred(this);
    }

    /**
     * Checks if the any person class instance has hit a game object
     * @param person a person object like enemy, player
     * @param object the GameObject collision has to be checked with (person, drop or other game objects)
     */
    public boolean hasCollisionOccurred(Person person, GameObject object) {
        float distance = returnDistance(person, object);
        return distance >= 0 && distance < 2;
    }

    /**
     * Returns the distance between any Person and any GameObject
     * @param person any Person instance
     * @param object any GameObject instance
     * @return float distance between the given parameters
     */
    public float returnDistance(Person person, GameObject object) {
        return (float) object.getDistanceFrom(person);
    }
}

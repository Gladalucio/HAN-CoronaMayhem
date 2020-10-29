package pedroroel.coronamayhem.controllers;

import nl.han.ica.oopg.objects.GameObject;
import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.entities.*;

import java.util.List;

public class CollisionController {
    private final CoronaMayhem world;

    public CollisionController(CoronaMayhem world) {
        this.world = world;
    }

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
        if (object.getDistanceFrom(person) >= 0 && object.getDistanceFrom(person) < 2) {
            return true;
        }
        return false;
    }
}

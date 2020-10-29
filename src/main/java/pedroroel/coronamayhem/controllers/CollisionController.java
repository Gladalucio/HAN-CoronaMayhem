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

//    /**
//     * Checks if the player has hit a game object
//     * @param person a person object like enemy, player
//     * @param object the GameObject collision has to be checked with
//     */
//    public void handleEntityCollision(Person person, GameObject object) {
//        if (object.getDistanceFrom(person) == 0.0) {
//            /* Collision has occurred */
//
//            if (person instanceof Player && object instanceof Enemy) {
//                /* Collision between player and enemy */
//
//            } else if (person instanceof Player && object instanceof Drop) {
//                /* Collision between player and drop */
//                if (object instanceof Virus) {
//
//                }
//            }
//        }
//    }
//
//    /**
//     * calculates and picks the current closest enemy to the player and handles possible collision
//     */
//    public void entityCollisionOccurred(Person person, GameObject object)
//    {
//        List<Enemy> enemiesList = world.getEnemyCtrl().getEnemiesList();
//
//        if (enemiesList.size() < 1) {
//            return;
//        }
//
//        double closestEnemyDistance = 10000.0;
//
//        for(Enemy ce : enemiesList)
//        {
//            if(ce.getDistanceFrom(person) <= closestEnemyDistance)
//            {
//                closestEnemyDistance = ce.getDistanceFrom(person);
//                closestEnemy = ce;
//            }
//        }
//        if(!isColliding && closestEnemy.getDistanceFrom(person) == 0.0)
//        {
//            if(person.getAngleFrom(closestEnemy) >= 160 && person.getAngleFrom(closestEnemy) <= 220)
//            {
//                System.out.println("Healed a patient!");
//                isColliding = true;
//                closestEnemy.decreaseLives();
//                world.getScoreboard().increase();
//            }else {
//                System.out.println("Infected!");
//                isColliding = true;
//                person.decreaseLives();
//                world.getScoreboard().decrease();
//            }
//        }
//        if(isColliding && closestEnemy.getDistanceFrom(person) != 0.0){
//            isColliding = false;
//        }
//    }
}

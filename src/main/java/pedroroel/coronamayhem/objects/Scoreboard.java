package pedroroel.coronamayhem.objects;

import pedroroel.coronamayhem.CoronaMayhem;

public class Scoreboard {
    private CoronaMayhem world;
    private static int score =0;
    private TextObject text = new TextObject("Score: " + score +" Lives: 1");

    public Scoreboard(CoronaMayhem world) {
        this.world = world;
        showScoreboard();
    }

    public void showScoreboard() {
        world.addGameObject(text, 540, 360);
    }

    public void increaseScore() {
        score++;
        update();
    }

    public void decreaseScore() {
        score--;
        update();
    }

    public void update() {
        text.setText("Score: " + score +" Lives: " + world.getPlayer().lives);
        text.update();
    }

//    /**
//     * checks if the player collides with an enemy and reduces his score by 1 each time
//     * TODO: move this ugly bit of code to enemyController and make it ~beautiful~
//     */
//    public void calculateScore()
//    {
//        if(world.getEnemyCtrl().getCollision() && i == 0)
//        {
//            if(world.getEnemyCtrl().getKilled() && i == 0)
//            {
//                i = 1;
//                score=score+1;
//                if(score % 5 == 0)
//                {
//                    world.getPlayer().increaseLives();
//                }
//            }else
//            {
//                i = 1;
//                score=score-1;
//                world.getPlayer().reduceLives();
//            }
//            text.setText("Score: " +score+" Lives: "+ world.getPlayer().lives);
//            text.update();
//        }
//        if(!world.getEnemyCtrl().getCollision() && i == 1)
//        {
//            i = 0;
//        }
//        if(world.getPlayer().lives <= 0)
//        {
//            world.pauseGame();
//        }
//    }
}

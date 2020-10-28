package pedroroel.coronamayhem.objects;

import pedroroel.coronamayhem.CoronaMayhem;

public class Scoreboard {
    private CoronaMayhem world;
    private static int score = 0;
    private TextObject text = new TextObject("Score: " + score + " Lives: 1");

    public Scoreboard(CoronaMayhem world) {
        this.world = world;
    }

    public int getScore() {
        return score;
    }

    public void show() {
        float calcX = world.width / 2 * .85f; /* Center of the screen with a slight offset to the left (width of gameobject) */
        float calcY = world.height / 18 * 7.25f; /* Based on the height of the screen divided by the number of tilerows */
        world.addGameObject(text, calcX, calcY);
    }

    public void increase() {
        score++;
        update();
    }

    public void decrease() {
        score--;
        update();
    }

    public Scoreboard reset() {
        score = 0;
        return this;
    }

    public void update() {
        text.setText("Score: " + score + " Lives: " + world.getPlayer().lives);
        text.update();
    }
}
package pedroroel.coronamayhem.objects;

import pedroroel.coronamayhem.CoronaMayhem;

public class Scoreboard {
    private final CoronaMayhem world;
    private static int score = 0;
    private final TextObject text;

    public Scoreboard(CoronaMayhem world) {
        this.world = world;
        text = new TextObject(returnText());
    }

    public int getScore() {
        return score;
    }

    public void show() {
        float calcX = world.width / 2 * .85f; /* Center of the screen with a slight offset to the left (width of gameObject) */
        float calcY = world.height / 18 * 7.25f; /* Based on the height of the screen divided by the number of tileRows */
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

    public void reset() {
        score = 0;
        update();
    }

    public void update() {
        text.setText(returnText());
        text.update();
    }

    private String returnText() {
        return "Score: " + score + " Lives: " + world.getPlayer().getLives();
    }
}
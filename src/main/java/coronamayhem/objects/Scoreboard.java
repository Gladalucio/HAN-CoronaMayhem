package coronamayhem.objects;

import coronamayhem.CoronaMayhem;

/**
 * Keeps score and shows in-game scoreboard text
 */
public class Scoreboard {
    private final CoronaMayhem world;
    private static int score = 0;
    private final TextObject text;

    public Scoreboard(CoronaMayhem world) {
        this.world = world;
        text = new TextObject(returnText());
    }

    /**
     * Returns the current score
     * @return current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds the scoreboard to the game
     */
    public void show() {
        float calcX = world.width / 2 * .85f; /* Center of the screen with a slight offset to the left (width of gameObject) */
        float calcY = world.height / 18 * 7.25f; /* Based on the height of the screen divided by the number of tileRows */
        world.addGameObject(text, calcX, calcY);
    }

    /**
     * Increases the score by 1 and updates
     */
    public void increase() {
        score++;
        update();
    }

    /**
     * Decreases the score by 1 and updates
     */
    public void decrease() {
        score--;
        update();
    }

    /**
     * Resets the score and updates
     */
    public void reset() {
        score = 0;
        update();
    }

    /**
     * Updates the text object and updates it so the correct values are displayed on the scoreboard
     */
    public void update() {
        text.setText(returnText());
        text.update();
    }

    /**
     * Returns the scoreboard text string in readable format
     * @return scoreboard text
     */
    private String returnText() {
        return "Score: " + score + " Lives: " + world.getPlayer().getLives();
    }
}
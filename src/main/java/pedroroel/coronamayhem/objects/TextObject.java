package pedroroel.coronamayhem.objects;

import nl.han.ica.oopg.objects.GameObject;
import processing.core.PGraphics;

/**
 * Used in Dashboards and Menus to show text
 */
public class TextObject extends GameObject {
    private String text;

    /**
     * Creates a TextObject that can show text on-screen
     * @param text Text to be set
     */
    public TextObject(String text) {
        this.text = text;
    }

    /**
     * Sets the in-game text
     * @param text Text to be set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Runs every cycle, updates text on screen
     * @param pg PGraphics
     */
    @Override
    public void draw(PGraphics pg) {
        pg.textAlign(pg.LEFT, pg.TOP);
        pg.textSize(25);
        pg.text(text, getX(), getY());
    }

    @Override
    public void update() {}
}

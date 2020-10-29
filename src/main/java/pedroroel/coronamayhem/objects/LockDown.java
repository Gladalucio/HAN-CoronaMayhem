package pedroroel.coronamayhem.objects;

import nl.han.ica.oopg.dashboard.Dashboard;
import nl.han.ica.oopg.objects.GameObject;
import pedroroel.coronamayhem.CoronaMayhem;
import processing.core.PGraphics;

public class LockDown extends GameObject {

    private final CoronaMayhem world;
    private Dashboard lockDownButton;
    private TextObject lockDownText;

    public LockDown(CoronaMayhem world) {
        this.world = world;
        lockDownText = new TextObject("LOCK \n DOWN");
    }

    public void showLargeButton() {
        float calcX = world.width / 2 * .93f; /* Center of the screen with a slight offset to the left (width of gameObject) */
        float calcY = world.height / 18 * 8.90f; /* Based on the height of the screen divided by the number of tileRows */
        lockDownButton = new Dashboard(calcX,calcY,100,80);
        lockDownButton.setBackground(10,100,100);
        lockDownButton.addGameObject(lockDownText, 10,0, 10);
        world.addGameObject(lockDownButton, calcX, calcY, 0);
    }

    @Override
    public void update()
    {

    }
    @Override
    public void draw(PGraphics pg)
    {

    }
}

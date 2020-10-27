package pedroroel.coronamayhem.objects;

import nl.han.ica.oopg.dashboard.Dashboard;
import pedroroel.coronamayhem.CoronaMayhem;

public class Menu {
    private final CoronaMayhem world;
    private final Dashboard menu = new Dashboard(350,160,500,500);

    public Menu(CoronaMayhem world) {
        this.world = world;
    }

    public void show() {
        TextObject playBtn = new TextObject("Press Enter to Play and Pause!");
        TextObject exitBtn = new TextObject("Press Escape to Exit!");

        world.addDashboard(menu);
        menu.setBackground(2,15,30);
        menu.addGameObject(playBtn, -240,1);
        menu.addGameObject(exitBtn, -240,150);
    }

    public void hide() {
        if(world.getDashboards().contains(menu))
        {
            world.deleteDashboard(menu);
        }
    }
}

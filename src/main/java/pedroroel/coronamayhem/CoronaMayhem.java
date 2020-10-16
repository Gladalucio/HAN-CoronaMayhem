package pedroroel.coronamayhem;

import nl.han.ica.oopg.dashboard.Dashboard;
import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.persistence.FilePersistence;
import nl.han.ica.oopg.persistence.IPersistence;
import nl.han.ica.oopg.sound.Sound;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.View;
import pedroroel.coronamayhem.tiles.BoardsTile;
import processing.core.PApplet;

public class CoronaMayhem extends GameEngine {
    private Sound backgroundSound;
    private Sound bubblePopSound;
    private TextObject dashboardText;
    private BubbleSpawner bubbleSpawner;
    private int bubblesPopped;
    private IPersistence persistence;
    private Player player;

    public static void main(String[] args) {
        String[] processingArgs = {"CoronaMayhem"};
        CoronaMayhem coronaMayhem = new CoronaMayhem();

        PApplet.runSketch(processingArgs, coronaMayhem);
    }

    @Override
    public void setupGame() {
        int worldWidth = 1200;
        int worldHeight = 900;

//        initializeSound();
        createDashboard(worldWidth, 100);
        initializeTileMap();
        initializePersistence();

        createObjects();
//        createBubbleSpawner();

        createViewWithoutViewport(worldWidth, worldHeight);
    }

    private void createViewWithoutViewport(int screenWidth, int screenHeight) {
        View view = new View(screenWidth, screenHeight);
        view.setBackground(loadImage("src/main/java/pedroroel/coronamayhem/assets/images/background.jpg"));

        setView(view);
        size(screenWidth, screenHeight);
    }

    private void initializeSound() {
        backgroundSound = new Sound(this, "src/main/java/pedroroel/coronamayhem/media/waterworld.mp3");
        backgroundSound.loop(-1);
        bubblePopSound = new Sound(this, "src/main/java/pedroroel/coronamayhem/media/pop.mp3");
    }

    private void createObjects() {
        player = new Player(this);
        addGameObject(player, 590, 725);
        Enemy sf = new Enemy(this);
        addGameObject(sf, 590, 220);
    }

    public void createBubbleSpawner() {
        bubbleSpawner = new BubbleSpawner(this, bubblePopSound, 2);
    }

    private void createDashboard(int dashboardWidth, int dashboardHeight) {
        Dashboard dashboard = new Dashboard(0, 0, dashboardWidth, dashboardHeight);
        dashboardText = new TextObject("");
        dashboard.addGameObject(dashboardText);
        addDashboard(dashboard);
    }

    /** File persistence */
    private void initializePersistence() {
        persistence = new FilePersistence("main/java/pedroroel/coronamayhem/media/bubblesPopped.txt");
        if (persistence.fileExists()) {
            bubblesPopped = Integer.parseInt(persistence.loadDataString());
            refreshDashboardText();
        }
    }

    private void initializeTileMap() {
        Sprite boardsSprite = new Sprite("src/main/java/pedroroel/coronamayhem/assets/images/tile.jpg");
        TileType<BoardsTile> boardTileType = new TileType<>(BoardsTile.class, boardsSprite);

        TileType[] tileTypes = {boardTileType};
        int tileSize = 50;
        int tilesMap[][] = { // x = 24, y = 18. backgroundsize should be 1200x900
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0}, // 0 = 6+6, -1 = 12
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1}, // 0 = 8, -1 = 16
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0}, // 0 = 4+4, -1 = 16
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0}, // 0 = 8+8, -1 = 8
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        tileMap = new TileMap(tileSize, tileTypes, tilesMap);
    }

    @Override
    public void update() {
    }

    private void refreshDashboardText() {
        dashboardText.setText("Bubbles popped: " + bubblesPopped);
    }

    public void increaseBubblesPopped() {
        bubblesPopped++;
        persistence.saveData(Integer.toString(bubblesPopped));
        refreshDashboardText();
    }
}

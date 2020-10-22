package pedroroel.coronamayhem;

import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.View;
import pedroroel.coronamayhem.entities.Enemy;
import pedroroel.coronamayhem.entities.Player;
import pedroroel.coronamayhem.controllers.EnemyController;
import pedroroel.coronamayhem.objects.GameTile;
import pedroroel.coronamayhem.objects.TextObject;
import processing.core.PApplet;

// HANICA OOPG JAVADOC: https://hanica.github.io/oopg/

public class CoronaMayhem extends GameEngine {
    private Player player;
    private EnemyController enemyCtrl;
    private static int score =0;
    private TextObject scoreText = new TextObject("Score: " +score);
    private int i = 0;


    public static void main(String[] args) {
        String[] processingArgs = {"CoronaMayhem"};
        CoronaMayhem coronaMayhem = new CoronaMayhem();
        PApplet.runSketch(processingArgs, coronaMayhem);
    }

    /**
     * Provides the setup for the game.
     */
    @Override
    public void setupGame() {
        int worldWidth = 1200;
        int worldHeight = 900;

        initializeTileMap();
        createObjects();

        createView(worldWidth, worldHeight);
    }

    /**
     * checks if the player collides with an enemy and reduces his score by 1 each time
     * TODO: summarize this into a function and leave update as clean as possible.
     */
    @Override
    public void update() {
        enemyCtrl.entityCollisionOccurred(player);
        if(enemyCtrl.getCollision() && i == 0)
        {
            if(enemyCtrl.getKilled() && i == 0)
            {
                i = 1;
                deleteGameObject(enemyCtrl.getClosestEnemy());
                score=score+1;
            }else
            {
                i = 1;
                score=score-1;
            }
            scoreText.setText("Score: " +score);
            scoreText.update();
        }
        else
            i = 1;
        if(!enemyCtrl.getCollision() && i == 1)
        {
            i = 0;
        }
    }
    /**
     * Creates the objects used.
     */
    private void createObjects() {
        player = new Player(this);
        addGameObject(player, 590, 725);

        enemyCtrl = new EnemyController(this);
        addGameObject(scoreText, 540, 360);
    }

    /**
     * Creates a new view, without a viewport for this game specifically.
     * @param screenWidth Game screen width
     * @param screenHeight Game screen height
     */
    private void createView(int screenWidth, int screenHeight) {
        View view = new View(screenWidth, screenHeight);
        view.setBackground(loadImage("src/main/java/pedroroel/coronamayhem/assets/images/background.jpg"));

        setView(view);
        size(screenWidth, screenHeight);
    }

    /**
     * TileMap is initialized, GameTiles put down.
     */
    private void initializeTileMap() {
        Sprite boardsSprite = new Sprite("src/main/java/pedroroel/coronamayhem/assets/images/tile.jpg");
        TileType<GameTile> boardTileType = new TileType<>(GameTile.class, boardsSprite);

        TileType[] tileTypes = {boardTileType};
        int tileSize = 50;
        int tilesMap[][] = { // x = 24, y = 18. backgroundsize should be 1200x900
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0}, // 0 = 6+6, -1 = 12
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1}, // 0 = 8, -1 = 16
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0}, // 0 = 5+5, -1 = 16
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

}

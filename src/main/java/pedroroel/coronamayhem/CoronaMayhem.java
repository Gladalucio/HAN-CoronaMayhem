package pedroroel.coronamayhem;

import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.View;
import pedroroel.coronamayhem.controllers.CollisionController;
import pedroroel.coronamayhem.controllers.DropController;
import pedroroel.coronamayhem.entities.Player;
import pedroroel.coronamayhem.controllers.EnemyController;
import pedroroel.coronamayhem.objects.GameTile;
import pedroroel.coronamayhem.objects.Menu;
import pedroroel.coronamayhem.objects.Scoreboard;
import processing.core.PApplet;

// HANICA OOPG JAVADOC: https://hanica.github.io/oopg/

/**
 * Base game
 */
public class CoronaMayhem extends GameEngine {
    public final String baseAssetPath = "src/main/java/pedroroel/coronamayhem/assets/";
    private Player player = new Player(this, 1);
    private EnemyController enemyCtrl = new EnemyController(this);
    private DropController dropCtrl = new DropController(this);
    private CollisionController collisionCtrl = new CollisionController(this);
    private Scoreboard scoreboard = new Scoreboard(this);
    private final Menu menu = new Menu(this);
    private boolean gameStarted = true;

    public static void main(String[] args) {
        String[] processingArgs = {"CoronaMayhem"};
        CoronaMayhem coronaMayhem = new CoronaMayhem();
        PApplet.runSketch(processingArgs, coronaMayhem);
    }

    //region Getters
    /**
     * Returns the Scoreboard instance
     * @return Scoreboard
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * Returns the Player instance
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the EnemyController instance
     * @return EnemyController
     */
    public EnemyController getEnemyCtrl() {
        return enemyCtrl;
    }

    /**
     * Returns the DropController instance
     * @return DropController
     */
    public DropController getDropCtrl() {
        return dropCtrl;
    }

    /**
     * Returns the current state of gameStarted
     * @return gameStarted
     */
    public boolean getGameStarted() {
        return gameStarted;
    }
    //endregion

    /**
     * Provides the setup for the game
     * Also re-run after reset
     */
    @Override
    public void setupGame() {
        int worldWidth = 1200;
        int worldHeight = 900;

        createView(worldWidth, worldHeight);
        initializeTileMap();
        addGameObject(player, 590, 500);
        enemyCtrl.startAlarm();
        dropCtrl.startAlarm();
        scoreboard.show();
        pause();
    }

    /**
     * Does collision checks every cycle
     */
    @Override
    public void update() {
        collisionCtrl.checkCollisions();
    }

    /**
     * Pauses the game state and shows the menu
     */
    @Override
    public void pause() {
        pauseGame();
        if(player.getLives() >= 0) {
            menu.showPauseScreen();
        } else {
            menu.showDeathScreen();
        }
        gameStarted = false;
    }

    /**
     * Resumes the game state and hides the menu if player lives >= 0
     * If not, the game is over and gets reset
     */
    @Override
    public void resume() {
        if (player.getLives() < 0) {
            reset();
        }
        menu.hide();
        resumeGame();
        gameStarted = true;
    }

    /**
     * Sets the objects to their default values and resets the EnemyController
     * Also re-runs setupGame();
     */
    public void reset() {
        deleteAllGameOBjects();
        enemyCtrl = new EnemyController(this);
        enemyCtrl.getEnemies().clear();
        player = new Player(this, 1);
        scoreboard = new Scoreboard(this);
        scoreboard.reset();
        setupGame();
    }

    /**
     * Creates a new view without a viewport
     * @param screenWidth Game screen width
     * @param screenHeight Game screen height
     */
    private void createView(int screenWidth, int screenHeight) {
        View view = new View(screenWidth, screenHeight);
        view.setBackground(loadImage(baseAssetPath + "images/background.jpg"));

        setView(view);
        size(screenWidth, screenHeight);
    }

    /**
     * TileMap is initialized, GameTiles put down
     */
    private void initializeTileMap() {
        Sprite boardsSprite = new Sprite("src/main/java/pedroroel/coronamayhem/assets/images/tile.jpg");
        TileType<GameTile> boardTileType = new TileType<>(GameTile.class, boardsSprite);

        TileType[] tileTypes = {boardTileType};
        int tileSize = 50;
        int tilesMap[][] = { // x = 24, y = 18
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

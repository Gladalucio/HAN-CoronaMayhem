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
import pedroroel.coronamayhem.objects.LockDown;
import pedroroel.coronamayhem.objects.Menu;
import pedroroel.coronamayhem.objects.Scoreboard;
import processing.core.PApplet;

// HANICA OOPG JAVADOC: https://hanica.github.io/oopg/

public class CoronaMayhem extends GameEngine {
    public final String baseAssetPath = "src/main/java/pedroroel/coronamayhem/assets/";
    private Player player = new Player(this, 1);
    private EnemyController enemyCtrl = new EnemyController(this);
    private DropController dropCtrl = new DropController(this);
    private CollisionController collisionCtrl = new CollisionController(this);
    private Scoreboard scoreboard = new Scoreboard(this);
    private LockDown lockDownButton = new LockDown(this);
    private final Menu menu = new Menu(this);
    private boolean gameStarted = true;

    public static void main(String[] args) {
        String[] processingArgs = {"CoronaMayhem"};
        CoronaMayhem coronaMayhem = new CoronaMayhem();
        PApplet.runSketch(processingArgs, coronaMayhem);
    }

    //region Getters
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyController getEnemyCtrl() {
        return enemyCtrl;
    }

    public DropController getDropCtrl() {
        return dropCtrl;
    }

    public boolean getGameStarted() {
        return gameStarted;
    }
    //endregion

    /**
     * Provides the setup for the game.
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
        lockDownButton.showLargeButton();
    }

    public void update() {
        collisionCtrl.checkCollisions();
    }

    /**
     * Pauses the game and shows the menu
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
     * Resumes the game and hides the menu if lives >= 0
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
        System.out.println("Reset game!");
        deleteAllGameOBjects();
        enemyCtrl = new EnemyController(this);
        getEnemyCtrl().getEnemies().clear();
        player = new Player(this, 1);
        scoreboard = new Scoreboard(this);
        scoreboard.reset();
        setupGame();
    }

    /**
     * Creates a new view, without a viewport for this game specifically.
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
     * TileMap is initialized, GameTiles put down.
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

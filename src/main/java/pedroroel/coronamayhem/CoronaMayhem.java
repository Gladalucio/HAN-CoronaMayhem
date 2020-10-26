package pedroroel.coronamayhem;

import nl.han.ica.oopg.dashboard.Dashboard;
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
    private TextObject scoreText = new TextObject("Score: " +score+" Lives: 1");
    private TextObject playButton = new TextObject("Play Game!");
    private TextObject exitButton = new TextObject("Exit Game!");
    private Dashboard menu = new Dashboard(350,160,500,500);
    private int i = 0;
    private boolean started = false;



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
        this.setGameSpeed(10);
    }


    @Override
    public void update() {
        System.out.println(mouseX +" : "+ mouseY);
        enemyCtrl.entityCollisionOccurred(player);
        calculateScore();
        Menu();
    }
    /**
     * checks if the player collides with an enemy and reduces his score by 1 each time
     * TODO: move this ugly bit of code to enemyController and make it ~beautiful~
     */
    private void calculateScore()
    {
        if(enemyCtrl.getCollision() && i == 0)
        {
            if(enemyCtrl.getKilled() && i == 0)
            {
                i = 1;
                score=score+1;
                if(score % 5 == 0)
                {
                    player.increaseLife();
                }
            }else
            {
                i = 1;
                score=score-1;
                player.reduceLife();
            }
            scoreText.setText("Score: " +score+" Lives: "+player.life);
            scoreText.update();
        }
        if(!enemyCtrl.getCollision() && i == 1)
        {
            i = 0;
        }
        if(player.life <= 0)
        {

            pauseGame();

        }
    }
    public void Menu()
    {

        if(started == false && mouseX >= 539 && mouseX <= 680 && mouseY >= 300 && mouseY <= 340)
        {
            started = true;
            deleteGameObject(player);
            deleteDashboard(menu);
            player = new Player(this, 1);
            addGameObject(player, 590, 400);
            this.setGameSpeed(60);
        }
        else if(started == false && mouseX >= 539 && mouseX <= 680 && mouseY >= 470 && mouseY <= 500)
        {
            this.exit();
        }
    }
    /**
     * Creates the objects used.
     */
    private void createObjects() {
        Dashboard deathScreen = new Dashboard(400,400,500,500);
        menu.setBackground(2,15,30);
        menu.addGameObject(playButton, -160,1);
        menu.addGameObject(exitButton, -160,150);
        addDashboard(menu);
        player = new Player(this, 500);
        addGameObject(player, 590, 300);

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

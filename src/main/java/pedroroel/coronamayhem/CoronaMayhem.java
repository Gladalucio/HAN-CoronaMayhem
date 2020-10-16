package pedroroel.coronamayhem;

import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.View;
import pedroroel.coronamayhem.entities.Enemy;
import pedroroel.coronamayhem.entities.Player;
import pedroroel.coronamayhem.tiles.GameTile;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class CoronaMayhem extends GameEngine {
    private Player player;
    private List<Enemy> enemiesList = new ArrayList<Enemy>();

    public static void main(String[] args) {
        String[] processingArgs = {"CoronaMayhem"};
        CoronaMayhem coronaMayhem = new CoronaMayhem();
        PApplet.runSketch(processingArgs, coronaMayhem);
    }

    @Override
    public void setupGame() {
        int worldWidth = 1200;
        int worldHeight = 900;

        initializeTileMap();
        createObjects();

        createViewWithoutViewport(worldWidth, worldHeight);
    }

    @Override
    public void update() {}

    private void createViewWithoutViewport(int screenWidth, int screenHeight) {
        View view = new View(screenWidth, screenHeight);
        view.setBackground(loadImage("src/main/java/pedroroel/coronamayhem/assets/images/background.jpg"));

        setView(view);
        size(screenWidth, screenHeight);
    }

    private void createObjects() {
        player = new Player(this);
        addGameObject(player, 590, 725);
        Enemy enemy = new Enemy(this, Enemy.Color.Yellow);
        addGameObject(enemy, 590, 220);
    }

    private void initializeTileMap() {
        Sprite boardsSprite = new Sprite("src/main/java/pedroroel/coronamayhem/assets/images/tile.jpg");
        TileType<GameTile> boardTileType = new TileType<>(GameTile.class, boardsSprite);

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

}

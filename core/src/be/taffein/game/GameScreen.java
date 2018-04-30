package be.taffein.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;


public class GameScreen implements Screen {

    private static final String X = "X";
    private static final String O = "O";
    private static final String WON = " won!";
    private static final String DRAW = "Draw!";

    private final TicTacToe game;

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private Texture xTexture;
    private Texture oTexture;
    private OrthographicCamera camera;

    private Tile[][] tiles;
    private Vector3 touchPos = new Vector3();
    private boolean playersTurn = true;
    private long lastPlayerTurn;
    private boolean gameEnded = false;
    private String gameOverText;
    private int turn = 0;
    private int randomNumber = 0;

    public GameScreen(final TicTacToe game) {
        this.game = game;
        spriteBatch = game.getSpriteBatch();
        font = game.getFont();

        xTexture = new Texture(Gdx.files.internal("x.png"));
        oTexture = new Texture(Gdx.files.internal("o.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 480);

        spriteBatch.setProjectionMatrix(camera.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.setProjectionMatrix(camera.combined);

        tiles = createTiles();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        drawGrid();
        drawTiles();

        if (!gameEnded) {
            if (playersTurn) {
                playerTurn();
            } else {
                opponentTurn();
            }
            checkWinCondition();
        } else {
            spriteBatch.begin();
            font.setColor(Color.BLACK);
            font.getData().setScale(3f, 3f);
            font.draw(spriteBatch, gameOverText, 180, 250);
            spriteBatch.end();
        }
    }

    private void checkWinCondition() {
         for (int i = 0; i <3; i++) {
             if (tiles[i][0].equals(tiles[i][1]) && tiles[i][1].equals(tiles[i][2])) {
                 gameEnded = true;
                 gameOverText = tiles[i][0].getValue() + WON;
             }

             if (tiles[0][i].equals(tiles[1][i]) && tiles[1][i].equals(tiles[2][i])) {
                 gameEnded = true;
                 gameOverText = tiles[0][i].getValue() + WON;
             }
         }

         if (tiles[0][0].equals(tiles[1][1]) && tiles[1][1].equals(tiles[2][2])) {
             gameEnded = true;
             gameOverText = tiles[0][0].getValue() + WON;
         }

        if (tiles[2][0].equals(tiles[1][1]) && tiles[1][1].equals(tiles[0][2])) {
            gameEnded = true;
            gameOverText = tiles[2][0].getValue() + WON;
        }

        if (!gameEnded) {
            if (tiles[0][0].getValue() != null && tiles[0][1].getValue() != null
                    && tiles[0][2].getValue() != null && tiles[1][0].getValue() != null
                    && tiles[1][1].getValue() != null && tiles[1][2].getValue() != null
                    && tiles[2][0].getValue() != null && tiles[2][1].getValue() != null
                    && tiles[2][2].getValue() != null) {
                gameEnded = true;
                gameOverText = DRAW;
            }
        }
    }

    private void opponentTurn() {
        if (turn == 1) {
            if (tiles[1][1].getValue() != null){
                // Player took center? Take random corner
                if (!playersTurn && TimeUtils.timeSinceNanos(lastPlayerTurn) > 1000000000) {
                    randomNumber = MathUtils.random(0, 3);
                    if (randomNumber == 0) {
                        tiles[0][0].setValue(O);
                        playersTurn = true;
                    }
                    if (randomNumber == 1) {
                        tiles[2][0].setValue(O);
                        playersTurn = true;
                    }
                    if (randomNumber == 2) {
                        tiles[0][2].setValue(O);
                        playersTurn = true;
                    }
                    if (randomNumber == 3) {
                        tiles[2][2].setValue(O);
                        playersTurn = true;
                    }
                }
            } else {
                // Take center
                if (!playersTurn && TimeUtils.timeSinceNanos(lastPlayerTurn) > 1000000000) {
                    tiles[1][1].setValue(O);
                    playersTurn = true;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = tiles[i][j];
                if (!playersTurn && tile.getValue() == null
                        && TimeUtils.timeSinceNanos(lastPlayerTurn) > 1000000000) {
                    tile.setValue(O);
                    playersTurn = true;
                }
            }
        }
    }

    private void playerTurn() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Tile tile = tiles[i][j];
                    if (tile.getRectangle().contains(touchPos.x, touchPos.y)) {
                        tile.setValue(X);
                        playersTurn = false;
                        lastPlayerTurn = TimeUtils.nanoTime();
                        turn++;
                    }
                }
            }
        }
    }

    private void drawTiles() {
        spriteBatch.begin();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = tiles[i][j];
                if (tile.getValue() != null) {
                    spriteBatch.draw(tile.getValue() == X ? xTexture : oTexture,
                            tile.getRectangle().x, tile.getRectangle().y,
                            tile.getRectangle().width, tile.getRectangle().height);
                }
            }
        }
        spriteBatch.end();
    }

    private void drawGrid() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(162, 0, 4, 480);
        shapeRenderer.rect(318, 0, 4, 480);
        shapeRenderer.rect(0, 162, 480, 4);
        shapeRenderer.rect(0, 318, 480, 4);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        xTexture.dispose();
        oTexture.dispose();
    }

    private Tile[][] createTiles() {
        Tile[][] tiles = new Tile[3][3];

        tiles[0][0] = new Tile(20, 20);
        tiles[0][1] = new Tile(176, 20);
        tiles[0][2] = new Tile(332, 20);

        tiles[1][0] = new Tile(20, 176);
        tiles[1][1] = new Tile(176, 176);
        tiles[1][2] = new Tile(332, 176);

        tiles[2][0] = new Tile(20, 332);
        tiles[2][1] = new Tile(176, 332);
        tiles[2][2] = new Tile(332, 332);

        return tiles;
    }
}

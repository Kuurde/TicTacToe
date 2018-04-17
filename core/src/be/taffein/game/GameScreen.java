package be.taffein.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;


public class GameScreen implements Screen {

    public static final String X = "X";
    public static final String O = "O";
    private final TicTacToe game;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private Texture xTexture;
    private Texture oTexture;
    private OrthographicCamera camera;
    private Tile[][] tiles;

    public GameScreen(final TicTacToe game) {
        this.game = game;
        spriteBatch = game.getSpriteBatch();

        xTexture = new Texture(Gdx.files.internal("x.png"));
        oTexture = new Texture(Gdx.files.internal("o.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 480);

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

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Tile tile = tiles[i][j];
                    if (tile.getRectangle().contains(touchPos.x, touchPos.y)) {
                        tile.setValue(X);
                    }
                }
            }
        }

        drawGrid();
        drawTiles();
    }

    private void drawTiles() {
        spriteBatch.setProjectionMatrix(camera.combined);
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

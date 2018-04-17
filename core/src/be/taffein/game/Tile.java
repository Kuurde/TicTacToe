package be.taffein.game;


import com.badlogic.gdx.math.Rectangle;

public class Tile {

    private Rectangle rectangle;
    private String value;

    public Tile(float x, float y) {
        rectangle = new Rectangle(x, y, 128, 128);
    }

    public Tile(float x, float y, String value) {
        rectangle = new Rectangle(x, y, 128, 128);
        this.value = value;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

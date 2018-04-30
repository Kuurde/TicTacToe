package be.taffein.game;


import com.badlogic.gdx.math.Rectangle;

public class Tile {

    private Rectangle rectangle;
    private String value;

    public Tile(float x, float y) {
        rectangle = new Rectangle(x, y, 128, 128);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        if (value == null || tile.value == null) {
            return false;
        }

        return value.equals(tile.value);

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}

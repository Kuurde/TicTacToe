package be.taffein.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TicTacToe extends Game {
	SpriteBatch spriteBatch;
	private BitmapFont font;

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
		font.dispose();
		this.getScreen().dispose();
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public BitmapFont getFont() {
		return font;
	}
}

package be.taffein.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import be.taffein.game.TicTacToe;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tic Tac Toe";
		config.width = 480;
		config.height = 480;
		new LwjglApplication(new TicTacToe(), config);
	}
}

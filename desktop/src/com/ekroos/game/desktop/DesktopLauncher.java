package com.ekroos.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ekroos.game.Program;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "nukkepeli";
		config.width = 900;
		config.height = 450;
		new LwjglApplication(new Program(), config);
	}
}

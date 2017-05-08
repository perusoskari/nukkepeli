package com.ekroos.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ekroos.game.Program;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Granny Cool";
		config.width = 1000;
		config.height = 500;
		new LwjglApplication(new Program(), config);
	}
}

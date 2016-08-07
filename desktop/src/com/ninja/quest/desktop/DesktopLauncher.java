package com.ninja.quest.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ninja.quest.NinjaQuest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "NinjaQuest";
		config.width = NinjaQuest.width;
		config.height = NinjaQuest.height;
		config.foregroundFPS = 60;
		config.backgroundFPS = 0;
		new LwjglApplication(new NinjaQuest(), config);

	}
}
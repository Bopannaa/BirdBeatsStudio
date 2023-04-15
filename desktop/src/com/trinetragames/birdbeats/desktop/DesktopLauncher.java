package com.trinetragames.birdbeats.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.trinetragames.birdbeats.BirdBeats;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "BirdBeats";
		config.width = 320;
		config.height = 480;
		new LwjglApplication(new BirdBeats(null), config);
	}
}

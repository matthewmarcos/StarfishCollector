package com.matthewmarcos.starfishcollector.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.matthewmarcos.starfishcollector.StarfishGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		new LwjglApplication(new StarfishGame(), "Starfish Collector", 800, 600);
	}
}

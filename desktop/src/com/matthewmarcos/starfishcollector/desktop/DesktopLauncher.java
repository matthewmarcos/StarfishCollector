package com.matthewmarcos.starfishcollector.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.matthewmarcos.starfishcollector.StarfishCollector;

public class DesktopLauncher {
	public static void main (String[] arg) {
		new LwjglApplication(new StarfishCollector(), "Starfish Collector", 800, 600);
	}
}

package com.matthewmarcos.starfishcollector.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.matthewmarcos.starfishcollector.StarfishCollectorAlpha;
import com.matthewmarcos.starfishcollector.StarfishCollectorBeta;

public class DesktopLauncher {
	public static void main (String[] arg) {
		new LwjglApplication(new StarfishCollectorBeta(), "Starfish Collector", 800, 600);
	}
}

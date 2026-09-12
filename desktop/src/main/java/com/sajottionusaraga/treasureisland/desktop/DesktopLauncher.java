package com.sajottionusaraga.treasureisland.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.sajottionusaraga.treasureisland.TreasureIslandGame;

/** Launches the desktop (LWJGL3) application, for quick testing on a PC before building for Android. */
public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Treasure Island: Lost Map");
        config.setWindowedMode(960, 540);
        config.useVsync(true);
        new Lwjgl3Application(new TreasureIslandGame(), config);
    }
}

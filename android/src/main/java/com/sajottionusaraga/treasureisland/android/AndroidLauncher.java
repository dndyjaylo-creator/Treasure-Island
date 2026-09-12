package com.sajottionusaraga.treasureisland.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.sajottionusaraga.treasureisland.TreasureIslandGame;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useAccelerometer = false;
        configuration.useCompass = false;
        initialize(new TreasureIslandGame(), configuration);
    }
}

package com.sajottionusaraga.treasureisland;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.sajottionusaraga.treasureisland.screens.MenuScreen;

/**
 * Root Game class for Treasure Island: Lost Map.
 * Holds shared rendering resources (SpriteBatch, font) and swaps screens
 * (Menu -> Play -> StageComplete) as the player progresses.
 */
public class TreasureIslandGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // default libGDX font; swap for a custom .fnt later if desired
        font.getData().setScale(1.5f);
        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render(); // delegates to the active Screen's render()
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        screen.dispose();
    }
}

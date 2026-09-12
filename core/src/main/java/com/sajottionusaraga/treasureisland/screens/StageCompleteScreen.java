package com.sajottionusaraga.treasureisland.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sajottionusaraga.treasureisland.TreasureIslandGame;

/**
 * Shown when Stage 1 (The Forgotten Beach) is finished.
 * Stages 2-5 (Jungle Path, Ancient Ruins, Hidden Cave, Treasure Temple) are not
 * yet implemented — tapping here returns to the menu for now.
 */
public class StageCompleteScreen implements Screen {

    private final TreasureIslandGame game;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport viewport;

    public StageCompleteScreen(TreasureIslandGame game) {
        this.game = game;
        viewport = new FitViewport(960, 540, camera);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.08f, 0.05f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            game.setScreen(new MenuScreen(game));
            dispose();
            return;
        }

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.setColor(Color.GOLD);
        game.font.getData().setScale(2.5f);
        game.font.draw(game.batch, "STAGE 1 COMPLETE!", -230, 60);
        game.font.getData().setScale(1.5f);
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "You recovered all 3 map fragments and reached", -260, 0);
        game.font.draw(game.batch, "the far shore of the Forgotten Beach.", -220, -30);
        game.font.draw(game.batch, "Stage 2: The Jungle Path is coming soon.", -240, -90);
        game.font.draw(game.batch, "Tap to return to the menu.", -150, -150);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}

package com.sajottionusaraga.treasureisland.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sajottionusaraga.treasureisland.TreasureIslandGame;

/** Title screen. Tap/click or press SPACE to begin Stage 1. */
public class MenuScreen implements Screen {

    private final TreasureIslandGame game;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport viewport;

    public MenuScreen(TreasureIslandGame game) {
        this.game = game;
        viewport = new FitViewport(960, 540, camera);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtilsClear();

        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new PlayScreen(game));
            dispose();
            return;
        }

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.setColor(Color.GOLD);
        game.font.getData().setScale(3f);
        game.font.draw(game.batch, "TREASURE ISLAND: LOST MAP", -320, 60);
        game.font.getData().setScale(1.5f);
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "Stage 1: The Forgotten Beach", -140, 0);
        game.font.draw(game.batch, "Tap the screen (or press SPACE) to start", -190, -60);
        game.batch.end();
    }

    private void ScreenUtilsClear() {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

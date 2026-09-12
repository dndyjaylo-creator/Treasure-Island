package com.sajottionusaraga.treasureisland.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sajottionusaraga.treasureisland.GameAssets;
import com.sajottionusaraga.treasureisland.TreasureIslandGame;
import com.sajottionusaraga.treasureisland.entities.Collectible;
import com.sajottionusaraga.treasureisland.entities.Gate;
import com.sajottionusaraga.treasureisland.entities.Player;
import com.sajottionusaraga.treasureisland.ui.VirtualJoystick;

/**
 * Stage 1: The Forgotten Beach.
 * A single-screen (non-scrolling) top-down level. Layout:
 *   - Left half: landing beach with 2 map fragments and the gate key.
 *   - A rock wall with one boulder-blocked corridor divides left from right.
 *   - Right half: reachable only once the boulder is unlocked with the key;
 *     holds the 3rd map fragment and the exit marker.
 * Collect all 3 map fragments, then reach the exit flag to complete the stage.
 */
public class PlayScreen implements Screen {

    private static final float WORLD_W = 960f;
    private static final float WORLD_H = 540f;

    private final TreasureIslandGame game;
    private final GameAssets assets = new GameAssets();
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport viewport;
    private final ShapeRenderer shapes = new ShapeRenderer();

    private final Player player;
    private final List<Collectible> collectibles = new ArrayList<>();
    private final Gate gate;
    private final List<Rectangle> solidWalls = new ArrayList<>();
    private final Rectangle exitZone = new Rectangle(900 - 24, 270 - 24, 48, 48);

    private final VirtualJoystick joystick;
    private boolean stageComplete = false;
    private String message = "";
    private float messageTimer = 0f;

    public PlayScreen(TreasureIslandGame game) {
        this.game = game;
        viewport = new FitViewport(WORLD_W, WORLD_H, camera);
        camera.position.set(WORLD_W / 2f, WORLD_H / 2f, 0);

        player = new Player(100, 270, assets.player);

        // Two map fragments on the accessible left side.
        collectibles.add(new Collectible(150, 150, Collectible.Type.MAP_PIECE, assets.mapPiece));
        collectibles.add(new Collectible(220, 430, Collectible.Type.MAP_PIECE, assets.mapPiece));
        // The gate key, tucked in a corner of the left side.
        collectibles.add(new Collectible(370, 480, Collectible.Type.KEY, assets.keyItem));
        // Third fragment only reachable after the gate opens.
        collectibles.add(new Collectible(760, 200, Collectible.Type.MAP_PIECE, assets.mapPiece));

        // Rock wall dividing left/right, with a gap at y=250-290 for the gate.
        solidWalls.add(new Rectangle(470, 290, 60, WORLD_H - 290));
        solidWalls.add(new Rectangle(470, 0, 60, 250));

        gate = new Gate(500, 270, assets.boulder);

        // Bottom-left joystick anchor (screen-space, y-up).
        joystick = new VirtualJoystick(110, 110, 70, 30);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        checkCollisions();

        Gdx.gl.glClearColor(0.12f, 0.12f, 0.16f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        drawWorld();
        drawHud();
        game.batch.end();

        // Joystick drawn in raw screen space (not affected by world camera).
        shapes.setProjectionMatrix(new com.badlogic.gdx.math.Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        // Joystick reads raw touch coords in real screen pixels, matching this projection.
        joystick.draw(shapes);
        shapes.end();

        if (stageComplete) {
            game.setScreen(new StageCompleteScreen(game));
            dispose();
        }
    }

    private void handleInput(float delta) {
        joystick.update();
        float dx = 0, dy = 0;

        // Keyboard (desktop testing).
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) dx -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) dx += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) dy += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) dy -= 1;

        // Touch joystick (Android).
        dx += joystick.getDx();
        dy += joystick.getDy();

        float oldX = player.x, oldY = player.y;
        player.move(dx, 0, delta, WORLD_W, WORLD_H);
        if (collidesSolid(player.getBounds())) player.x = oldX;
        player.move(0, dy, delta, WORLD_W, WORLD_H);
        if (collidesSolid(player.getBounds())) player.y = oldY;

        if (messageTimer > 0) messageTimer -= delta;
    }

    private boolean collidesSolid(Rectangle playerBounds) {
        for (Rectangle wall : solidWalls) {
            if (playerBounds.overlaps(wall)) return true;
        }
        if (!gate.unlocked && playerBounds.overlaps(gate.getBounds())) return true;
        return false;
    }

    private void checkCollisions() {
        Rectangle bounds = player.getBounds();

        for (Collectible c : collectibles) {
            if (!c.collected && bounds.overlaps(c.getBounds())) {
                c.collected = true;
                if (c.type == Collectible.Type.MAP_PIECE) {
                    player.mapPiecesCollected++;
                    showMessage("Map fragment recovered! (" + player.mapPiecesCollected + "/3)");
                } else {
                    player.hasKey = true;
                    showMessage("You found the gate key!");
                }
            }
        }

        Rectangle gateApproach = new Rectangle(
                gate.x - Gate.SIZE, gate.y - Gate.SIZE, Gate.SIZE * 2, Gate.SIZE * 2);
        if (!gate.unlocked && player.hasKey && bounds.overlaps(gateApproach)) {
            gate.unlocked = true;
            showMessage("The boulder rolls aside!");
        }

        if (player.mapPiecesCollected >= 3 && bounds.overlaps(exitZone)) {
            stageComplete = true;
        }
    }

    private void showMessage(String text) {
        message = text;
        messageTimer = 2.5f;
    }

    private void drawWorld() {
        // Sand background.
        for (float wx = 0; wx < WORLD_W; wx += 64) {
            for (float wy = 0; wy < WORLD_H; wy += 64) {
                game.batch.draw(assets.sandTile, wx, wy, 64, 64);
            }
        }
        // Water border to sell the "island" framing.
        for (float wx = 0; wx < WORLD_W; wx += 64) {
            game.batch.draw(assets.waterTile, wx, -32, 64, 32);
            game.batch.draw(assets.waterTile, wx, WORLD_H, 64, 32);
        }

        for (Rectangle wall : solidWalls) {
            game.batch.draw(assets.boulder, wall.x, wall.y, wall.width, wall.height);
        }
        gate.draw(game.batch);

        for (Collectible c : collectibles) {
            c.draw(game.batch);
        }

        game.batch.draw(assets.exitFlag, exitZone.x, exitZone.y, exitZone.width, exitZone.height);

        player.draw(game.batch);
    }

    private void drawHud() {
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "Map Fragments: " + player.mapPiecesCollected + "/3", 20, WORLD_H - 15);
        game.font.draw(game.batch, "Key: " + (player.hasKey ? "Obtained" : "Not found"), 20, WORLD_H - 45);
        if (player.mapPiecesCollected >= 3) {
            game.font.setColor(Color.FOREST);
            game.font.draw(game.batch, "Head to the flag on the far right!", WORLD_W - 380, WORLD_H - 15);
        }
        if (messageTimer > 0) {
            game.font.setColor(Color.GOLD);
            game.font.draw(game.batch, message, WORLD_W / 2f - 180, 40);
        }
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
        assets.dispose();
        shapes.dispose();
    }
}

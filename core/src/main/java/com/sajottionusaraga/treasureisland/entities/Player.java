package com.sajottionusaraga.treasureisland.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/** The player character: a circle that moves freely within world bounds at a fixed speed. */
public class Player {

    public static final float SIZE = 28f;
    public static final float SPEED = 260f; // world units per second

    public float x, y;
    private final Texture texture;
    public boolean hasKey = false;
    public int mapPiecesCollected = 0;

    public Player(float startX, float startY, Texture texture) {
        this.x = startX;
        this.y = startY;
        this.texture = texture;
    }

    /** Moves the player by a normalized direction vector, clamped to the given world bounds. */
    public void move(float dx, float dy, float delta, float worldWidth, float worldHeight) {
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        if (len > 1f) {
            dx /= len;
            dy /= len;
        }
        x += dx * SPEED * delta;
        y += dy * SPEED * delta;
        x = MathUtils.clamp(x, SIZE / 2f, worldWidth - SIZE / 2f);
        y = MathUtils.clamp(y, SIZE / 2f, worldHeight - SIZE / 2f);
    }

    public Rectangle getBounds() {
        return new Rectangle(x - SIZE / 2f, y - SIZE / 2f, SIZE, SIZE);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x - SIZE / 2f, y - SIZE / 2f, SIZE, SIZE);
    }
}

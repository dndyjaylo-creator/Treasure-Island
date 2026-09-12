package com.sajottionusaraga.treasureisland.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * A boulder blocking the path to the Ancient Ruins area of the beach.
 * Solid (blocks player movement) until the player has collected the key,
 * at which point it is removed ("unlocked") the moment the player touches it.
 */
public class Gate {

    public static final float SIZE = 56f;

    public final float x, y;
    private final Texture texture;
    public boolean unlocked = false;

    public Gate(float x, float y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    public Rectangle getBounds() {
        return new Rectangle(x - SIZE / 2f, y - SIZE / 2f, SIZE, SIZE);
    }

    public void draw(SpriteBatch batch) {
        if (!unlocked) {
            batch.draw(texture, x - SIZE / 2f, y - SIZE / 2f, SIZE, SIZE);
        }
    }
}

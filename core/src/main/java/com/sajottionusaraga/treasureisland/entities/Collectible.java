package com.sajottionusaraga.treasureisland.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/** A pickup on the map: either a treasure-map fragment or the gate key, depending on `type`. */
public class Collectible {

    public enum Type { MAP_PIECE, KEY }

    public static final float SIZE = 28f;

    public final float x, y;
    public final Type type;
    private final Texture texture;
    public boolean collected = false;

    public Collectible(float x, float y, Type type, Texture texture) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.texture = texture;
    }

    public Rectangle getBounds() {
        return new Rectangle(x - SIZE / 2f, y - SIZE / 2f, SIZE, SIZE);
    }

    public void draw(SpriteBatch batch) {
        if (!collected) {
            batch.draw(texture, x - SIZE / 2f, y - SIZE / 2f, SIZE, SIZE);
        }
    }
}

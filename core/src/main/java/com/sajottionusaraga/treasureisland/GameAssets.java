package com.sajottionusaraga.treasureisland;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Builds every texture the game needs at runtime out of simple shapes (Pixmap),
 * so the project runs with zero external art assets. Replace any of these with a
 * real sprite later by swapping the Texture returned here for one loaded from
 * an image file in android/assets.
 */
public class GameAssets {

    public final Texture player;
    public final Texture sandTile;
    public final Texture waterTile;
    public final Texture mapPiece;
    public final Texture keyItem;
    public final Texture boulder;
    public final Texture exitFlag;
    public final Texture whitePixel;

    public GameAssets() {
        player = circleTexture(28, Color.NAVY);
        sandTile = solidTexture(64, 64, new Color(0.86f, 0.76f, 0.52f, 1f));
        waterTile = solidTexture(64, 64, new Color(0.20f, 0.45f, 0.75f, 1f));
        mapPiece = diamondTexture(28, Color.GOLD);
        keyItem = keyTexture(28, Color.YELLOW);
        boulder = solidTexture(56, 56, new Color(0.45f, 0.42f, 0.40f, 1f));
        exitFlag = flagTexture(36, Color.FOREST);
        whitePixel = solidTexture(1, 1, Color.WHITE);
    }

    private Texture solidTexture(int w, int h, Color color) {
        Pixmap pm = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        pm.setColor(color);
        pm.fill();
        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }

    private Texture circleTexture(int diameter, Color color) {
        Pixmap pm = new Pixmap(diameter, diameter, Pixmap.Format.RGBA8888);
        pm.setColor(color);
        pm.fillCircle(diameter / 2, diameter / 2, diameter / 2);
        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }

    private Texture diamondTexture(int size, Color color) {
        Pixmap pm = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pm.setColor(color);
        int half = size / 2;
        pm.fillTriangle(half, 0, size, half, half, size);
        pm.fillTriangle(half, 0, 0, half, half, size);
        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }

    private Texture keyTexture(int size, Color color) {
        Pixmap pm = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pm.setColor(color);
        pm.fillCircle(size / 4, size / 4, size / 4);
        pm.fillRectangle(size / 4, size / 4, size - size / 4, size / 8);
        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }

    private Texture flagTexture(int size, Color color) {
        Pixmap pm = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pm.setColor(Color.DARK_GRAY);
        pm.fillRectangle(size / 2 - 2, 0, 4, size);
        pm.setColor(color);
        pm.fillTriangle(size / 2, 2, size, size / 4, size / 2, size / 2);
        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }

    public void dispose() {
        player.dispose();
        sandTile.dispose();
        waterTile.dispose();
        mapPiece.dispose();
        keyItem.dispose();
        boulder.dispose();
        exitFlag.dispose();
        whitePixel.dispose();
    }
}

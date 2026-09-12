package com.sajottionusaraga.treasureisland.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * A simple on-screen joystick anchored to the bottom-left of the screen.
 * Touch/click and drag within its radius to get a normalized direction vector.
 * Works with touch (Android) and mouse (desktop testing) since both use Gdx.input.
 */
public class VirtualJoystick {

    private final float baseX, baseY, baseRadius, knobRadius;
    private float knobX, knobY;
    private boolean active = false;
    private int activePointer = -1;

    public VirtualJoystick(float baseX, float baseY, float baseRadius, float knobRadius) {
        this.baseX = baseX;
        this.baseY = baseY;
        this.baseRadius = baseRadius;
        this.knobRadius = knobRadius;
        this.knobX = baseX;
        this.knobY = baseY;
    }

    /** Call once per frame with the touch position already converted to screen-space (y-up, origin bottom-left). */
    public void update() {
        boolean found = false;
        for (int p = 0; p < 4; p++) {
            if (Gdx.input.isTouched(p)) {
                float tx = Gdx.input.getX(p);
                float ty = Gdx.graphics.getHeight() - Gdx.input.getY(p);
                float dist = Vector2.dst(tx, ty, baseX, baseY);
                if (dist <= baseRadius * 2.2f || (active && activePointer == p)) {
                    found = true;
                    active = true;
                    activePointer = p;
                    float dx = tx - baseX;
                    float dy = ty - baseY;
                    float len = (float) Math.sqrt(dx * dx + dy * dy);
                    if (len > baseRadius) {
                        dx = dx / len * baseRadius;
                        dy = dy / len * baseRadius;
                    }
                    knobX = baseX + dx;
                    knobY = baseY + dy;
                    break;
                }
            }
        }
        if (!found) {
            active = false;
            activePointer = -1;
            knobX = baseX;
            knobY = baseY;
        }
    }

    public float getDx() {
        return active ? (knobX - baseX) / baseRadius : 0f;
    }

    public float getDy() {
        return active ? (knobY - baseY) / baseRadius : 0f;
    }

    public void draw(ShapeRenderer shapes) {
        shapes.setColor(1f, 1f, 1f, 0.25f);
        shapes.circle(baseX, baseY, baseRadius);
        shapes.setColor(Color.WHITE);
        shapes.circle(knobX, knobY, knobRadius);
    }
}

package edu.csuci.clickyplane.managers.input;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyInput {

    private static final boolean[] keys;
    private static final boolean[] pkeys;

    static final Vector2 mouse;

    private static final int NUM_KEYS = 3;
    public static final int CLICK = 0;
    public static final int ENTER = 1;
    public static final int BACK = 2;

    static {
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];
        mouse = new Vector2();
    }

    public static void update() {
        for (int i = 0; i < NUM_KEYS; ++i) {
            pkeys[i] = keys[i];
        }
    }

    static void setKey(int key, boolean value) {
        keys[key] = value;
    }

    public static boolean keyCheck(int key) {
        return keys[key];
    }

    public static boolean keyCheckPressed(int key) {
        return keys[key] && !pkeys[key];
    }

    public static Vector2 screenMouse() {
        return mouse.cpy();
    }

    public static Vector2 worldMouse(Viewport viewport) {
        return viewport.unproject(screenMouse());
    }

}

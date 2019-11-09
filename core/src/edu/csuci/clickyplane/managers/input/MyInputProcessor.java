package edu.csuci.clickyplane.managers.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class MyInputProcessor extends InputAdapter {

    @Override
    public boolean keyDown(int keycode) {
        return processKey(keycode, true);
    }

    @Override
    public boolean keyUp(int keycode) {
        return processKey(keycode, false);
    }

    private boolean processKey(int keycode, boolean value) {
        boolean processed = false;
        if(keycode == Input.Keys.SPACE) {
            MyInput.setKey(MyInput.CLICK, value);
            processed = true;
        }
        else if(keycode == Input.Keys.ENTER) {
            MyInput.setKey(MyInput.ENTER, value);
            processed = true;
        }
        else if(keycode == Input.Keys.ESCAPE) {
            MyInput.setKey(MyInput.BACK, value);
            processed = true;
        }
        return processed;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        MyInput.mouse.set(screenX, screenY);
        if(button == Input.Buttons.LEFT) {
            MyInput.setKey(MyInput.CLICK, true);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        MyInput.mouse.set(screenX, screenY);
        if(button == Input.Buttons.LEFT) {
            MyInput.setKey(MyInput.CLICK, false);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        MyInput.mouse.set(screenX, screenY);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        MyInput.mouse.set(screenX, screenY);
        return true;
    }
}

package edu.csuci.clickyplane;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.csuci.clickyplane.gamestates.GameStateType;
import edu.csuci.clickyplane.managers.GameStateManager;

public class ClickyPlane extends ApplicationAdapter {

	private GameStateManager gsm;

	@Override
	public void create () {
		this.gsm = new GameStateManager(GameStateType.PLAY);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float dt = Gdx.graphics.getDeltaTime();
		this.gsm.step(dt);
	}

	@Override
	public void resize(int width, int height) {
		this.gsm.resize(width, height);
	}

	@Override
	public void dispose () {
		this.gsm.dispose();
	}
}

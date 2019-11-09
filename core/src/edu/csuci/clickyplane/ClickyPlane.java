package edu.csuci.clickyplane;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.csuci.clickyplane.gamestates.GameStateType;
import edu.csuci.clickyplane.managers.ContentManager;
import edu.csuci.clickyplane.managers.GameStateManager;
import edu.csuci.clickyplane.managers.input.MyInput;
import edu.csuci.clickyplane.managers.input.MyInputProcessor;

public class ClickyPlane extends ApplicationAdapter {

	public static final int WORLD_WIDTH = 1280;
	public static final int WORLD_HEIGHT = 720;

	private GameStateManager gsm;
	public static ContentManager content;

	@Override
	public void create () {
		content = new ContentManager();
		this.gsm = new GameStateManager(GameStateType.PLAY);
		Gdx.input.setInputProcessor(new MyInputProcessor());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float dt = Gdx.graphics.getDeltaTime();
		this.gsm.step(dt);

		MyInput.update();
	}

	@Override
	public void resize(int width, int height) {
		this.gsm.resize(width, height);
	}

	@Override
	public void dispose () {
		content.dispose();
		this.gsm.dispose();
	}
}

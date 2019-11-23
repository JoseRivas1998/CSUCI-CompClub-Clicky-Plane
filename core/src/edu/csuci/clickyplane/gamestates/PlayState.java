package edu.csuci.clickyplane.gamestates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.csuci.clickyplane.ClickyPlane;
import edu.csuci.clickyplane.entities.DualPipe;
import edu.csuci.clickyplane.entities.Ground;
import edu.csuci.clickyplane.entities.LabelEntity;
import edu.csuci.clickyplane.entities.Plane;
import edu.csuci.clickyplane.managers.ContentManager;
import edu.csuci.clickyplane.managers.GameStateManager;
import edu.csuci.clickyplane.managers.input.MyInput;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayState extends AbstractGameState {

    public static final float PIPE_SPAWN_TIME = 1f;

    private Viewport viewport;
    private List<DualPipe> pipes;
    private float pipeSpawnTimer;

    private Plane plane;

    private Ground ground;

    private boolean isAlive;
    private boolean isStarted;

    private int score;
    private LabelEntity scoreLabel;

    private Texture background;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        viewport = new FitViewport(ClickyPlane.WORLD_WIDTH, ClickyPlane.WORLD_HEIGHT);

        pipes = new ArrayList<DualPipe>();
        pipeSpawnTimer = 0f;

        plane = new Plane();

        ground = new Ground();

        isAlive = true;
        isStarted = false;

        score = 0;
        scoreLabel = new LabelEntity();
        scoreLabel.setAlign(LabelEntity.MIDDLE_CENTER);
        scoreLabel.setFont(ContentManager.Font.SCORE);
        scoreLabel.setPosition(
                ClickyPlane.WORLD_WIDTH * 0.5f,
                ClickyPlane.WORLD_HEIGHT * 0.75f
        );

        background = ClickyPlane.content.getTexture(ContentManager.Image.BACKGROUND);

    }

    @Override
    public void handleInput(float dt) {
        if(isAlive && isStarted) {
            plane.handleInput();
        } else if(isStarted) {
            // TODO handle game over
            if(MyInput.keyCheckPressed(MyInput.CLICK)) {
                switchState(GameStateType.PLAY);
            }
        } else {
            if(MyInput.keyCheckPressed(MyInput.CLICK)) {
                isStarted = true;
                plane.jump();
            }
        }
    }

    @Override
    public void update(float dt) {
        if((isAlive || !plane.collidingWith(ground)) && isStarted) {
            updatePlane(dt);
        }

        if(isAlive && isStarted) {
            updatePlayEnvironment(dt);
        } else if(isStarted) {
            // TODO update game over
        } else {
            updateAwaitingInput(dt);
        }
        updateScoreLabel(dt);
        viewport.apply(true);
    }

    private void updateScoreLabel(float dt) {
        scoreLabel.setText(String.valueOf(score));
        scoreLabel.update(dt);
    }

    private void updatePlane(float dt) {
        plane.update(dt);
        if(plane.collidingWith(ground)) {
            isAlive = false;
        }
    }

    private void updateAwaitingInput(float dt) {
        ground.update(dt);
    }

    private void updatePlayEnvironment(float dt) {
        updatePipes(dt);
        spawnPipes(dt);
        ground.update(dt);
    }

    private void updatePipes(float dt) {
        Iterator<DualPipe> pipeIterator = pipes.iterator();
        while (pipeIterator.hasNext()) {
            DualPipe pipe = pipeIterator.next();
            pipe.update(dt);
            if(pipe.collidingWith(plane)) {
                isAlive = false;
            }
            if(plane.getCenterX() > pipe.getCenterX() && !pipe.hasGotPoint()) {
                score++;
                pipe.point();
                ClickyPlane.content.playSound(ContentManager.SoundEffect.POINT);
            }
            if (pipe.getX() + pipe.getWidth() < 0) {
                pipe.dispose();
                pipeIterator.remove();
            }
        }
    }

    private void spawnPipes(float dt) {
        pipeSpawnTimer += dt;
        if (pipeSpawnTimer >= PIPE_SPAWN_TIME) {
            pipeSpawnTimer = 0;
            pipes.add(new DualPipe());
        }
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        sb.draw(background, 0, 0, ClickyPlane.WORLD_WIDTH, ClickyPlane.WORLD_HEIGHT);
        for (DualPipe pipe : pipes) {
            pipe.draw(dt, sb, sr);
        }
        ground.draw(dt, sb, sr);
        plane.draw(dt, sb, sr);
        scoreLabel.draw(dt, sb, sr);
        sb.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        for (DualPipe pipe : pipes) {
            pipe.dispose();
        }
        pipes.clear();
        plane.dispose();
        ground.dispose();
    }
}

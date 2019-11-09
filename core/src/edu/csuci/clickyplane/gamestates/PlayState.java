package edu.csuci.clickyplane.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.csuci.clickyplane.ClickyPlane;
import edu.csuci.clickyplane.entities.DualPipe;
import edu.csuci.clickyplane.entities.Plane;
import edu.csuci.clickyplane.managers.GameStateManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayState extends AbstractGameState {

    public static final float PIPE_SPAWN_TIME = 1f;

    private Viewport viewport;
    private List<DualPipe> pipes;
    private float pipeSpawnTimer;

    private Plane plane;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        viewport = new FitViewport(ClickyPlane.WORLD_WIDTH, ClickyPlane.WORLD_HEIGHT);

        pipes = new ArrayList<DualPipe>();
        pipeSpawnTimer = 0f;

        plane = new Plane();

    }

    @Override
    public void handleInput(float dt) {
        plane.handleInput();
    }

    @Override
    public void update(float dt) {
        plane.update(dt);
        updatePipes(dt);
        spawnPipes(dt);
        viewport.apply(true);
    }

    private void updatePipes(float dt) {
        Iterator<DualPipe> pipeIterator = pipes.iterator();
        while (pipeIterator.hasNext()) {
            DualPipe pipe = pipeIterator.next();
            pipe.update(dt);
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
        for (DualPipe pipe : pipes) {
            pipe.draw(dt, sb, sr);
        }
        plane.draw(dt, sb, sr);
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
    }
}

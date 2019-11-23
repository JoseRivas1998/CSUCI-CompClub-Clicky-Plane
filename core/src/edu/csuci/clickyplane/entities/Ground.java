package edu.csuci.clickyplane.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import edu.csuci.clickyplane.ClickyPlane;
import edu.csuci.clickyplane.managers.ContentManager;

public class Ground extends AbstractEntity {

    private Texture ground;
    private float drawX;

    public Ground() {
        super();
        ground = ClickyPlane.content.getTexture(ContentManager.Image.GROUND_GRASS);
        setSize(ground.getWidth(), ground.getHeight());
        setVelocityX(-Pipe.PIPE_SPEED);
        drawX = 0;
    }

    @Override
    public void update(float dt) {
        drawX += getVelocityX() * dt;
        if(drawX < -getWidth()) {
            drawX += getWidth();
        }
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        float x = drawX;
        while(x <= ClickyPlane.WORLD_WIDTH) {
            sb.draw(ground, x, 0);
            x += ground.getWidth();
        }
    }

    @Override
    public void dispose() {

    }
}

package edu.csuci.clickyplane.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import edu.csuci.clickyplane.ClickyPlane;
import edu.csuci.clickyplane.MyHelpers;
import edu.csuci.clickyplane.managers.ContentManager;
import edu.csuci.clickyplane.managers.input.MyInput;

public class Plane extends AbstractSpriteEntity {

    private static final float ANIMATION_SPEED = 0.05f;

    private static final float GRAVITY = 1000f;
    private static final float JUMP_SPEED = 500f;
    private static final float ANGLE_SMOOTHING = 7f;

    private Animation<TextureRegion> anim;
    private float stateTime;

    public Plane() {
        super();
        Texture spritesheet = ClickyPlane.content.getTexture(ContentManager.Image.PLANE_SPRITESHEET);
        TextureRegion[][] planes = MyHelpers.splitSpriteSheet(spritesheet, 4, 3);
        TextureRegion[] frames = MyHelpers.choose(planes);

        anim = new Animation<TextureRegion>(ANIMATION_SPEED, frames);
        anim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        stateTime = 0;
        setSize(frames[0].getRegionWidth(), frames[0].getRegionHeight());
        setCenter(ClickyPlane.WORLD_WIDTH * 0.15f, ClickyPlane.WORLD_HEIGHT * 0.5f);

    }

    public void handleInput() {
        if (MyInput.keyCheckPressed(MyInput.CLICK)
                && getY() < ClickyPlane.WORLD_HEIGHT - getHeight()) {
            jump();
        }
    }

    public void jump() {
        setVelocityY(JUMP_SPEED);
    }

    @Override
    public void update(float dt) {
        setVelocityY(getVelocityY() - GRAVITY * dt);
        applyVelocity(dt);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        animate(dt);
        rotate();
        super.draw(dt, sb, sr);
    }

    private void animate(float dt) {
        stateTime += dt;
        setImage(anim.getKeyFrame(stateTime));
        centerOrigin(true);
    }

    private void rotate() {
        Vector2 simMovement = new Vector2(Pipe.PIPE_SPEED, getVelocityY());
        float targetAngle = simMovement.angle();
        float diff;
        if(getVelocityY() > 0) {
            if(imageAngle > targetAngle) {
                if(imageAngle > 270) {
                    diff = targetAngle + (360 - imageAngle);
                    imageAngle += diff / ANGLE_SMOOTHING;
                }
            } else {
                diff = targetAngle - imageAngle;
                imageAngle += diff / ANGLE_SMOOTHING;
                if(imageAngle > targetAngle) {
                    imageAngle = targetAngle;
                }
            }
        } else if (getVelocityY() < 0) {
            if (imageAngle > targetAngle) {
                diff = imageAngle - targetAngle;
                imageAngle -= diff / ANGLE_SMOOTHING;
            } else {
                diff = imageAngle + (360 - targetAngle);
                imageAngle -= diff / ANGLE_SMOOTHING;
            }
        }
        if (imageAngle < 0) {
            imageAngle += 360;
        }
        if (imageAngle > 360) {
            imageAngle -= 360;
        }
    }

    @Override
    public void dispose() {

    }

}

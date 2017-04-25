package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Puoskari on 25.4.2017.
 */

public class BlueLady {
    private TextureRegion currentFrame;
    private float stateTime;
    private Rectangle rectangle;
    private Animator animator;
    float y;

    public BlueLady() {
        animator = new Animator();
        stateTime = 0.0f;
        animator.createAnimation("Sininenleidi10.png", stateTime, 5, 2, 1 / 15f);
        currentFrame = animator.getCurrentFrame(stateTime);
        y = 0.6f;
        rectangle = new Rectangle(-1f, y, currentFrame.getRegionWidth()/30f,
                currentFrame.getRegionHeight()/30f);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(currentFrame, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void move() {

        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animator.getCurrentFrame(stateTime);

        if (rectangle.x < 0.05f) {
            rectangle.x += Gdx.graphics.getDeltaTime() * 0.15f;
        }

        rectangle.y = (y + MathUtils.sin((stateTime))/6f);
    }
}

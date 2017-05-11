package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Puoskari on 25.4.2017.
 * Scary ghost that haunts Ekroos.
 */

public class BlueLady {
    private TextureRegion currentFrame;
    private float stateTime;
    private Rectangle rectangle;
    private Animator animator;
    private float y;
    private float x;
    private boolean onPosition;
    private float cosCounter;

    /**
     * The ghost that follows Ekroos.
     */
    public BlueLady() {
        animator = new Animator();
        stateTime = 0.0f;
        animator.createAnimation("Sininenleidi10.png", stateTime, 5, 2, 1 / 15f);
        currentFrame = animator.getCurrentFrame(stateTime);
        y = 0.6f;
        rectangle = new Rectangle(-1f, y, currentFrame.getRegionWidth()/30f,
                currentFrame.getRegionHeight()/30f);
    }

    /**
     * Get the hitbox of the rectangle.
     * @return  the hitbox.
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Draws the Blue Lady
     * @param batch used to draw.
     */
    public void draw(SpriteBatch batch) {
        batch.draw(currentFrame, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    /**
     * Moves the Blue Lady.
     */
    public void move() {

        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animator.getCurrentFrame(stateTime);

        if (rectangle.x < 0.05f && !onPosition) {
            rectangle.x += Gdx.graphics.getDeltaTime() * 0.15f;
            x = rectangle.x;
        } else {
            onPosition = true;
        }

        if (onPosition) {
            int b = MathUtils.random(10);

            if (b <= 3) {
                cosCounter += Gdx.graphics.getDeltaTime();
                rectangle.x = (x + MathUtils.sin((cosCounter)) / 10f);
            }
        }

        rectangle.y = (y + MathUtils.sin((stateTime))/6f);
    }
}

package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Puoskari on 13.4.2017.
 */

public class Ghost {
    private Animator animator;
    private Array<Ghost> ghostList;
    private TextureRegion currentFrame;
    private Rectangle rectangle;
    private float stateTime;
    private float moveSpeed;

    public Ghost(Array<Ghost> list) {
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.35f;
        stateTime = 0.0f;
        ghostList = list;
        animator = new Animator();
        animator.createAnimation("traps/spookSheet8.png", stateTime, 8, 1, 1 / 10f);
        currentFrame = animator.getCurrentFrame(stateTime);
        rectangle = new Rectangle(10.2f, 0.7f, currentFrame.getRegionWidth()/60f,
                currentFrame.getRegionHeight()/60f);
    }

    public void move() {
        rectangle.x -= moveSpeed;
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animator.getCurrentFrame(stateTime);
        checkForDispose();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(currentFrame, rectangle.x, rectangle.y, rectangle.getWidth(),
                rectangle.getHeight());
    }

    public void checkForDispose() {
        if (rectangle.x + rectangle.width < 0f) {
            ghostList.removeValue(this, true);
        }
    }

    public void destroy() {
        ghostList.removeValue(this, true);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}

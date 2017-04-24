package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 13.4.2017.
 */

public class WaterDollHelp {
    private Rectangle rectangle;
    private Texture flyDollTexture;
    private Rectangle flyDollRectangle;
    private TextureRegion currentFrame;
    private Animator animator;
    private float flySpeed;
    private Array<WaterDollHelp> list;
    private boolean flyDollOntop;
    private float stateTime;
    private float moveSpeed;
    private boolean frozen;

    public WaterDollHelp(Array<WaterDollHelp> list, float towardsX, float towardsY) {
        flyDollTexture = new Texture(Gdx.files.internal("dollsAndHelps/doll.png"));
        stateTime = 0.0f;
        animator = new Animator();
        animator.createAnimation("dollsAndHelps/iceSheet6.png", stateTime, 6, 1, 1 / 30f);
        animator.animation.setPlayMode(Animation.PlayMode.NORMAL);
        currentFrame = animator.getCurrentFrame(stateTime);
        frozen = false;
        Gdx.input.vibrate(150);

        flyDollRectangle = new Rectangle(-0.5f, 0.6f - (flyDollTexture.getHeight()/60f), flyDollTexture.getWidth()/60f,
                flyDollTexture.getHeight()/60f);

        rectangle = new Rectangle(towardsX - 0.1f, towardsY, currentFrame.getRegionWidth()/54f,
                currentFrame.getRegionHeight()/60f);

        flySpeed = Gdx.graphics.getDeltaTime() * 2.5f;
        this.list = list;
        flyDollOntop = false;
    }

    public void checkForDispose() {
        if (rectangle.x + rectangle.width < 0) {
            flyDollTexture.dispose();
            list.removeValue(this, true);
        }
    }

    public boolean getFrozen() {
        return frozen;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(flyDollTexture, flyDollRectangle.x, flyDollRectangle.y + flyDollRectangle.height,
                flyDollRectangle.getWidth(), flyDollRectangle.getHeight());
        if (flyDollOntop) {
            batch.draw(currentFrame, rectangle.x, rectangle.y - rectangle.height,
                    rectangle.width, rectangle.height);
        }
    }

    public void dispose() {
        flyDollTexture.dispose();
    }

    public void move() {
        flyDollRectangle.x += flySpeed;
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f;
        rectangle.x -= moveSpeed;

        if (flyDollOntop) {
            frozen = true;
            stateTime += Gdx.graphics.getDeltaTime();
        }

        if (flyDollOntop) {
            if (!animator.animation.isAnimationFinished(stateTime)) {
                currentFrame = animator.getCurrentFrame(stateTime);
           }
        }

        if (flyDollRectangle.overlaps(rectangle)) {
            flyDollOntop = true;
        }
    }
}

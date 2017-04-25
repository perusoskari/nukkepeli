package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Puoskari on 2.3.2017.
 */

public class Ekroos {
    private Texture texture;
    private float gravity = 0.1f;
    private Rectangle rectangle;
    private boolean givenUp;
    Animator animator;
    TextureRegion currentFrame;
    private float stateTime;

    public Ekroos(float x, float y) {
        texture = new Texture(Gdx.files.internal("ekroos.png"));
        animator = new Animator();
        stateTime = 0.0f;
        animator.createAnimation("ekroosSheet6.png", stateTime, 6, 1, 1 / 16f);
        currentFrame = animator.getCurrentFrame(stateTime);
        rectangle = new Rectangle(x, y, currentFrame.getRegionWidth()/35f,
                currentFrame.getRegionHeight()/35f);
    }

    /**
     * gravity takes hold of ekroos' fate
     * @param basicTileUnder true if there is basic tile under ekroos.
     */
    public void gravityPull(boolean basicTileUnder, boolean secure, BasicTile basicTile) {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animator.getCurrentFrame(stateTime);

        if (!basicTileUnder && !secure) {
            rectangle.y -= gravity;
        }

        if (basicTileUnder || secure) {
            rectangle.y = basicTile.getHeight();
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(currentFrame, rectangle.x, rectangle.getY(), rectangle.width, rectangle.height);
    }

    public float get_x() {
        return rectangle.getX();
    }

    public float get_y() {
        return rectangle.getY();
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

}

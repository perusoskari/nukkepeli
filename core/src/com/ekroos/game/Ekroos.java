package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Puoskari on 2.3.2017.
 */

public class Ekroos {
    private Texture texture;
    private float gravity = 0.1f;
    private Rectangle rectangle;
    private Rectangle ditchRect;
    private boolean givenUp;
    Animator animator;
    TextureRegion currentFrame;
    private float stateTime;
    private MainDoll mainDoll;

    /**
     * The Granny Cool that you as a player are trying to defend against the traps.
     * @param x
     * @param y
     */
    public Ekroos(float x, float y, MainDoll mainDoll) {
        this.mainDoll = mainDoll;
        texture = new Texture(Gdx.files.internal("ekroos.png"));
        animator = new Animator();
        stateTime = 0.0f;
        animator.createAnimation("ekroosSheet6.png", stateTime, 6, 1, 1 / 20f);
        currentFrame = animator.getCurrentFrame(stateTime);
        rectangle = new Rectangle(x, y, currentFrame.getRegionWidth()/35f,
                currentFrame.getRegionHeight()/35f);
        ditchRect = new Rectangle(x - (rectangle.width/4), y, rectangle.width * 1.5f,
                rectangle.height * 1.5f);
    }

    /**
     * Gravity takes hold of Ekroos' fate.
     * @param basicTileUnder true if there is basic tile under Ekroos.
     */
    public void gravityPull(boolean basicTileUnder, boolean secure, BasicTile basicTile) {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animator.getCurrentFrame(stateTime);

        if (!basicTileUnder && !secure) {
            rectangle.y -= gravity;
            ditchRect.y = rectangle.y;
        }

        if (basicTileUnder || secure) {
            rectangle.y = basicTile.getHeight();
            ditchRect.y = rectangle.y;
        }
    }

    /**
     * Draws Ekroos.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(currentFrame, rectangle.x, rectangle.getY(), rectangle.width, rectangle.height);
    }

    /**
     * Check if player taps near Ekroos and if one does, end the game.
     * @param camera to get relative x and y of fingers position;
     */
    public void checkForDollDitch(OrthographicCamera camera) {
        Vector3 vector3 = new Vector3();

        if (Gdx.input.isTouched()) {
            vector3.x = Gdx.input.getX();
            vector3.y = Gdx.input.getY();
            camera.unproject(vector3);

            if (ditchRect.contains(vector3.x, vector3.y)) {
                mainDoll.startFlight();
            }
        }
    }

    /**
     * Get the x coordinate of Ekroos.
     * @return
     */
    public float get_x() {
        return rectangle.getX();
    }

    /**
     * Get the y coordinate of Ekroos.
     * @return
     */
    public float get_y() {
        return rectangle.getY();
    }

    /**
     * Disposes the texture.
     */
    public void dispose() {
        texture.dispose();
    }

    /**
     * Get the hitbox of Ekroos.
     * @return
     */
    public Rectangle getRectangle() {
        return rectangle;
    }
}

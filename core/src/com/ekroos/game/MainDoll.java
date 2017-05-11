package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Puoskari on 31.3.2017.
 */

public class MainDoll {
    Texture texture;
    TextureRegion textureRegion;
    Rectangle rectangle;
    private boolean isCarried;
    private boolean flyMyChild;
    private float counter;

    /**
     * The main doll (called lost Erik) Ekroos holds and Blue Lady tries to get back.
     */
    public MainDoll() {
        texture = new Texture(Gdx.files.internal("dollsAndHelps/Vauvanukke1.png"));
        rectangle = new Rectangle(1.8f, 0.4f, texture.getWidth()/120f, texture.getHeight()/120f);
        textureRegion = new TextureRegion(texture);
        textureRegion.flip(true, false);
        isCarried = true;
        flyMyChild = false;
        counter = 0;
    }

    /**
     * Starts the flight if player decides go give up and ditch the doll.
     */
    public void startFlight() {
        isCarried = false;
        flyMyChild = true;
    }

    /**
     * Moves the doll and makes it fly if its ditched.
     * @param x
     * @param y
     */
    public void move(float x, float y) {

        if (isCarried) {
            rectangle.x = x - 0.1f;
            rectangle.y = y - 0.12f;
        }

        if (flyMyChild) {
            if (counter < 179) {
                counter += 0.1;
            }

            rectangle.x -= 0.03f;
            rectangle.y = y + (MathUtils.sin(counter));

            if (rectangle.y <= y - rectangle.getHeight()) {
                flyMyChild = false;
            }
        }
    }

    /**
     * Gets if player has surrendered.
     * @return
     */
    public boolean checkForSurrender() {
        if (!isCarried && !flyMyChild) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Draws the doll.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(textureRegion, rectangle.x, rectangle.y, rectangle.getWidth(), rectangle.getHeight());
    }

    /**
     * Disposes the texture.
     */
    public void dispose() {
        texture.dispose();
    }
}

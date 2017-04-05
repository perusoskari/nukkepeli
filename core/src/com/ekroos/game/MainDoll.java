package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Puoskari on 31.3.2017.
 */

public class MainDoll {
    Texture texture;
    Rectangle rectangle;
    private boolean isCarried;
    private boolean flyMyChild;
    private float counter;

    public MainDoll() {
        texture = new Texture(Gdx.files.internal("dollsAndHelps/doll.png"));
        rectangle = new Rectangle(2f, 1f, texture.getWidth()/60f, texture.getHeight()/60f);
        isCarried = true;
        flyMyChild = false;
        counter = 0;
    }

    public void startFlight() {
        isCarried = false;
        flyMyChild = true;
    }

    public void move(float x, float y) {


        if (isCarried) {
            rectangle.x = x;
            rectangle.y = y;
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

    public boolean checkForPause() {
        if (!isCarried && !flyMyChild) {
            return true;
        } else {
            return false;
        }
    }

    public void draw(SpriteBatch batch) {
        //batch.draw(texture, rectangle.x, rectangle.y, rectangle.getWidth(), rectangle.getHeight());
    }

    public void dispose() {
        texture.dispose();
    }
}

package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 23.4.2017.
 */

public class DrumDollHelp {
    private Rectangle rectangle;
    private Texture texture;
    private float moveSpeed;
    private Array<DrumDollHelp> list;
    private boolean hasNullified;

    public DrumDollHelp(float x, float y, Array<DrumDollHelp> list) {
        this.list = list;
        texture = new Texture(Gdx.files.internal("dollsAndHelps/doll.png"));
        rectangle = new Rectangle(x, y, texture.getWidth()/60f, texture.getHeight()/60f);
        hasNullified = false;
        Gdx.input.vibrate(150);
    }

    public Boolean hasNullified() {
        return  hasNullified;
    }

    public void move() {
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f * 3f;
        rectangle.x += moveSpeed;

        if (rectangle.x > 10f) {
            dispose();
            list.removeValue(this, true);
        }
    }
    public void dispose() {
        texture.dispose();
        list.removeValue(this, true);
    }
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void setNullified() {
        hasNullified = true;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}


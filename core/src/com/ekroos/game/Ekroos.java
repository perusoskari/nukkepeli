package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Puoskari on 2.3.2017.
 */

public class Ekroos {
    private Texture texture;
    private float gravity = 0.1f;
    private Rectangle rectangle;

    public Ekroos(float x, float y) {
        texture = new Texture(Gdx.files.internal("ekroos.png"));
        rectangle = new Rectangle(x, y, texture.getWidth()/50f,texture.getHeight()/50f);
    }

    /**
     * gravity takes hold of ekroos' fate
     * @param basicTileUnder true if there is basic tile under ekroos.
     */
    public void gravityPull(boolean basicTileUnder, boolean secure, BasicTile basicTile) {
        if (!basicTileUnder && !secure) {
            rectangle.y -= gravity;
        }


    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.getY(), rectangle.width, rectangle.height);
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

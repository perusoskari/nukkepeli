package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 2.3.2017.
 */

public class TrapTile implements AllTiles{
    private Texture texture;
    private Rectangle rectangle;
    private Array<TrapTile> trapTiles;
    private float moveSpeed;
    private boolean safe;
    private String trapType;
    boolean nullified;

    public TrapTile(String textureName, float x, float y, Array<TrapTile> array) {
        safe = false;
        trapTiles = array;
        texture = new Texture(Gdx.files.internal("traps/" + textureName));
        rectangle = new Rectangle(x, y, texture.getWidth()/60f, texture.getHeight()/60f);
        rectangle.setX(x);
        rectangle.setY(y);
        nullified = false;

        addType(textureName);

        if (trapType.equals("3")) {
            rectangle.setHeight(texture.getHeight()/132.25f);
        }
    }

    public void addType(String trapName) {
        if (trapName.equals("pimeys.png")) {
            trapType = "1";
        }

        if (trapName.equals("piikkiansa.png")) {
            trapType = "2";
        }

        if (trapName.equals("weight.png")) {
            trapType = "3";
        }
    }

    public void move() {
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f;

        float x = rectangle.getX() - moveSpeed;
        rectangle.setX(x);
        overMap();
    }

    public float getWidth() {
        return rectangle.getWidth();
    }

    public float get_x() {
        return rectangle.getX();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * removes the tile if its not visible on the map anymore
     */
    public void overMap() {
        if (rectangle.getX() < 0 - rectangle.getWidth()) {
            trapTiles.removeValue(this, true);
            dispose();
        }

    }

    public void draw(SpriteBatch batch) {

        if (!trapType.equals("3")) {
            batch.draw(texture, rectangle.getX(), rectangle.getY(), rectangle.getWidth(),
                    rectangle.getHeight());
        } else {
            batch.draw(texture, rectangle.getX(), rectangle.getY(), rectangle.getWidth(),
                    texture.getHeight()/60f);
        }
    }

    public String getTrapType() {
        return trapType;
    }

    public void setSafe() {
        safe = true;
    }
    public boolean getIfTileIsSafe() {
        return safe;
    }

    public void dispose() {
        texture.dispose();
    }

    public void nullify() {
        if (trapType.equals("3")) {
            texture.dispose();
            texture = new Texture(Gdx.files.internal("traps/weight2.png"));
            nullified = true;
        }
    }

    public boolean isNullified() {
        return nullified;
    }
}


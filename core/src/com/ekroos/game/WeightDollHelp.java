package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 3.4.2017.
 */

public class WeightDollHelp {
    float towardsX;
    float towardsY;
    Rectangle rectangle;
    Texture texture;
    float moveSpeed;
    Array<WeightDollHelp> list;

    public WeightDollHelp(float x, float y, Array<WeightDollHelp> list,
                          float towardsX, float towardsY) {
        texture = new Texture(Gdx.files.internal("puunukke.png"));
        rectangle = new Rectangle(x, y, texture.getWidth()/60f, texture.getHeight()/60f);
        moveSpeed = 0.08f;
        this.towardsX = towardsX;
        this.towardsY = towardsY;
        this.list = list;
    }

    public void move() {
        towardsX -= 0.02f;

            if (rectangle.getX() != towardsX + 0.55f) {
                rectangle.x = towardsX + 0.55f;
            }

            if (rectangle.getY() >= towardsY) {
                rectangle.y -= moveSpeed;
            }
    }

    public void checkForDispose() {
        if (rectangle.x + texture.getWidth() < 0) {
            texture.dispose();
            list.removeValue(this, true);
        }
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y,
                rectangle.getWidth(), rectangle.getHeight());
    }

    public void dispose() {
        texture.dispose();
    }
}

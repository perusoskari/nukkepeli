package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 22.4.2017.
 */

public class ZombieDollHelp {
    private Texture texture;
    private Rectangle rectangle;
    private float towardsX;
    private float towardsY;
    private float moveSpeed;
    private Array<ZombieDollHelp> list;

    public ZombieDollHelp(float x, float y, Array<ZombieDollHelp> list, float towardsX, float towardsY) {
        this.list = list;
        this.towardsX = towardsX;
        this.towardsY = towardsY;
        texture = new Texture(Gdx.files.internal("dollsAndHelps/doll.png"));
        Gdx.input.vibrate(150);
        rectangle = new Rectangle(x, y, texture.getWidth()/60f, texture.getHeight()/60f);
    }

    public void dispose() {
        texture.dispose();
        list.removeValue(this, true);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void move() {
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f;
        towardsX -= moveSpeed;
        rectangle.x -= moveSpeed * 4.5f;

        if (rectangle.x <= towardsX) {
            dispose();
        }

    }
    public Rectangle getRectangle() {
        return rectangle;
    }

}

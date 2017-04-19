package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Puoskari on 19.4.2017.
 */

public class PickUpDoll {
    Texture texture;
    Rectangle rectangle;
    float moveSpeed;
    String type;
    Array<PickUpDoll> list;

    public PickUpDoll(float x, float y, String type, Array<PickUpDoll> list) {
        texture = new Texture(Gdx.files.internal("dollsAndHelps/doll.png"));
        rectangle = new Rectangle(x, y, texture.getWidth()/60f, texture.getHeight()/60f);
        this.type = type;
        this.list = list;
        list.add(this);
        System.out.println("created");
    }

    public void move() {
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f;
        rectangle.x -= moveSpeed;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void dispose() {
        texture.dispose();
        list.removeValue(this, true);
    }

    public String getType() {
        return type;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void checkIfOverTheMap() {
        if (rectangle.x + rectangle.width < -0.5f) {
            dispose();
        }
    }
}

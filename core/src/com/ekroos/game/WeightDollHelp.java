package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 3.4.2017.
 */

public class WeightDollHelp {
    private float towardsX;
    private float towardsY;
    private Rectangle rectangle;
    private Texture texture;
    private float moveSpeed;
    private float moveSpeedTowardsTrap;
    private Array<WeightDollHelp> list;

    public WeightDollHelp(float x, float y, Array<WeightDollHelp> list,
                          float towardsX, float towardsY) {
        texture = new Texture(Gdx.files.internal("dollsAndHelps/puunukke.png"));
        rectangle = new Rectangle(x, y, texture.getWidth()/60f, texture.getHeight()/60f);

        this.towardsX = towardsX;
        this.towardsY = towardsY;
        this.list = list;
        Gdx.input.vibrate(150);
    }

    public void move() {
        moveSpeedTowardsTrap = Gdx.graphics.getDeltaTime() * 1.2f * 4f;
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f;
        towardsX -= moveSpeed;

            if (rectangle.getX() != towardsX + 0.55f) {
                rectangle.x = towardsX + 0.55f;
            }

            if (rectangle.getY() >= towardsY) {
                rectangle.y -= moveSpeedTowardsTrap;
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

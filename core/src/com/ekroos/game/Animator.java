package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 9.2.2017.
 */

public class Animator {
    private TextureRegion currentFrame;
    public Animation<TextureRegion> animation;
    private Texture texture;
    private int rows;
    private int cols;

    public void createAnimation(String nameOfTexture, float stateTime,
                                int col, int row, float animationSpeed) {
        rows = row;
        cols = col;

        texture = new Texture(Gdx.files.internal(nameOfTexture));

        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth()/col,
                texture.getHeight()/row);
        TextureRegion[] array1d = makeIt1d(tmp);
        animation = new Animation<TextureRegion>(animationSpeed, array1d);
        currentFrame = animation.getKeyFrame(stateTime, true);
    }

    public TextureRegion getCurrentFrame(float stateTime) {

        currentFrame = animation.getKeyFrame(stateTime, true);

        return currentFrame;
    }

    public TextureRegion[] makeIt1d(TextureRegion[][] tmp) {
        TextureRegion[] animation = new TextureRegion[rows * cols];
        int count = 0;

        for (int i = 0;i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                animation[count] = tmp[i][j];
                count++;
            }
        }

        return animation;
    }

    public void dispose() {
        texture.dispose();
    }
}
